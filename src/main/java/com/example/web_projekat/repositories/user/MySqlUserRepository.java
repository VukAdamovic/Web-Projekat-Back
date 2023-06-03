package com.example.web_projekat.repositories.user;

import com.example.web_projekat.entities.User;
import com.example.web_projekat.repositories.MySqlAbstractRepository;
import com.example.web_projekat.repositories.dto.user.UserCreateDto;
import com.example.web_projekat.repositories.dto.user.UserUpdateDto;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserRepository extends MySqlAbstractRepository implements UserRepository {

    @Override
    public User createUser(UserCreateDto userCreateDto) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        User user = new User(userCreateDto.getEmail(), userCreateDto.getFirstName(),
                userCreateDto.getLastName(), userCreateDto.getRoleId(), DigestUtils.sha256Hex(userCreateDto.getHashedPassword())); //setujem mu sve vrednosti od createDto i ove defaultne
        user.setStatus(true);


        try{
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("insert into raf_users (email, firstName, lastName, roleId, status, hashedPassword) values (?,?,?,?,?,?)", generatedColumns);

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setInt(4, user.getRoleId());
            preparedStatement.setBoolean(5, true);
            preparedStatement.setString(6, user.getHashedPassword());

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }


        }catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public List<User> getAllUser(int page) {
        List<User> allUsers = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        int itemsPerPage = 10;
        int startIndex = (page - 1) * itemsPerPage;

        try {
            connection = this.newConnection();
            statement = connection.prepareStatement("SELECT * FROM raf_users LIMIT ?, ?");
            statement.setInt(1, startIndex);
            statement.setInt(2, itemsPerPage);
            resultSet = statement.executeQuery();


            while (resultSet.next()){
                allUsers.add(new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getInt("roleId"), resultSet.getBoolean("status"), resultSet.getString("hashedPassword")));
            }

            // Provera da li postoji sledeći page
            if (allUsers.isEmpty() && page > 1) {
                int previousPage = page - 1;
                return getAllUser(previousPage);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return allUsers;
    }

    @Override
    public User findUserById(int id) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM raf_users where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();



            while (resultSet.next()){
                user = new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getInt("roleId"), resultSet.getBoolean("status"), resultSet.getString("hashedPassword"));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public User findUserByEmailAndPassword(String email, String hashedPassword) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM raf_users where email = ? and hashedPassword = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, hashedPassword);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                user = new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getInt("roleId"), resultSet.getBoolean("status"), resultSet.getString("hashedPassword"));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return user;
    }

    @Override
    public User updateUser(int id, UserUpdateDto userUpdateDto) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;


        try {
            connection = this.newConnection();

            if(userUpdateDto.getRoleId() == 1){
                preparedStatement = connection.prepareStatement("UPDATE raf_users SET status = ? WHERE id = ?");
                preparedStatement.setBoolean(1, true);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            }

            preparedStatement = connection.prepareStatement("UPDATE raf_users SET firstName = ?, lastName = ?, email = ?, roleId = ? WHERE id = ?");
            preparedStatement.setString(1, userUpdateDto.getFirstName());
            preparedStatement.setString(2, userUpdateDto.getLastName());
            preparedStatement.setString(3, userUpdateDto.getEmail());
            preparedStatement.setInt(4, userUpdateDto.getRoleId());
            preparedStatement.setInt(5, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                String selectQuery = "SELECT id, email, firstName, lastName, roleId, status, hashedPassword FROM raf_users WHERE id = ?";
                preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    user = new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getInt("roleId"), resultSet.getBoolean("status"), resultSet.getString("hashedPassword"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResultSet(resultSet);
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public User changeActivation(int id) {
        User user = findUserById(id);

        if(user.getRoleId() != 1) {
            user.setStatus(!user.getStatus());

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                connection = this.newConnection();

                preparedStatement = connection.prepareStatement("UPDATE raf_users SET status = ? WHERE id = ?");
                preparedStatement.setBoolean(1, user.getStatus());
                preparedStatement.setInt(2, id);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    String selectQuery = "SELECT id, email, firstName, lastName, roleId, status, hashedPassword FROM raf_users WHERE id = ?";
                    preparedStatement = connection.prepareStatement(selectQuery);
                    preparedStatement.setInt(1, id);
                    resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        user = new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getInt("roleId"), resultSet.getBoolean("status"), resultSet.getString("hashedPassword"));
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                this.closeResultSet(resultSet);
                this.closeStatement(preparedStatement);
                this.closeConnection(connection);
            }
        }



        return user;

    }

    @Override
    public void deleteUserById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM raf_users where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
}
