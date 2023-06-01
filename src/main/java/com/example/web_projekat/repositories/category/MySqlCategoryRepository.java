package com.example.web_projekat.repositories.category;

import com.example.web_projekat.entities.Category;
import com.example.web_projekat.entities.News;
import com.example.web_projekat.repositories.MySqlAbstractRepository;
import com.example.web_projekat.repositories.dto.category.CategoryDto;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlCategoryRepository extends MySqlAbstractRepository implements CategoryRepository {


    @Override
    public Category createCategory(CategoryDto categoryDto) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Category category = new Category(categoryDto.getName(), categoryDto.getDescription());

        try{
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("insert into categories (name, description) values (?,?)", generatedColumns);

            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());


            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                category.setId(resultSet.getInt(1));
            }


        }catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return category;
    }

    @Override
    public List<Category> getAllCategory(int page) {
        List<Category> allCategories = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        int itemsPerPage = 10;
        int startIndex = (page - 1) * itemsPerPage;

        try {
            connection = this.newConnection();

            statement = connection.prepareStatement("SELECT * FROM categories LIMIT ?, ?");
            statement.setInt(1, startIndex);
            statement.setInt(2, itemsPerPage);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                allCategories.add(new Category(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description")));
            }

            // Provera da li postoji sledeći page
            if(allCategories.isEmpty() && page > 1){
                int previousPage = page - 1;
                return getAllCategory(previousPage);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return allCategories;
    }

    @Override
    public List<Category> getAllCategory() {
        List<Category> allCategories = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            statement = connection.prepareStatement("SELECT * FROM categories");
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                allCategories.add(new Category(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description")));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return allCategories;
    }

    @Override
    public Category findCategoryById(int id) {
        Category category = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM categories where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                category = new Category(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description"));
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

        return category;
    }

    @Override
    public List<News> getNewsByCategoryId(int id, int page) {
        List<News> allNews = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int itemsPerPage = 10;
        int startIndex = (page - 1) * itemsPerPage;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE categoryId = ? LIMIT ?, ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, startIndex);
            preparedStatement.setInt(3, itemsPerPage);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                allNews.add(new News(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("content"),
                        resultSet.getString("createdAt"), resultSet.getInt("visitNumber"), resultSet.getInt("categoryId"), resultSet.getInt("userId")));
            }

            // Provera da li postoji sledeći page
            if (allNews.isEmpty() && page > 1) {
                int previousPage = page - 1;
                return getNewsByCategoryId(id ,previousPage);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return allNews;
    }

    @Override
    public Category updateCategory(int id, CategoryDto categoryDto) {
        Category category = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE categories SET name = ?, description = ? WHERE id = ?");
            preparedStatement.setString(1, categoryDto.getName());
            preparedStatement.setString(2, categoryDto.getDescription());
            preparedStatement.setInt(3, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                String selectQuery = "SELECT id, name, description FROM categories WHERE id = ?";
                preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    category = new Category(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResultSet(resultSet);
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return category;
    }

    @Override
    public void deleteCategoryById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM categories where id = ?");
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
