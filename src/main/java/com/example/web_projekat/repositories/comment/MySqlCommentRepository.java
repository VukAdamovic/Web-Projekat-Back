package com.example.web_projekat.repositories.comment;


import com.example.web_projekat.entities.Comment;
import com.example.web_projekat.repositories.MySqlAbstractRepository;
import com.example.web_projekat.repositories.dto.comment.CommentDto;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MySqlCommentRepository extends MySqlAbstractRepository implements CommentRepository {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy H:mm:ss");


    @Override
    public Comment createComment(CommentDto commentDto) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Comment comment = new Comment(commentDto.getName(), commentDto.getContent(), LocalDate.now().format(DATE_FORMATTER), commentDto.getNewsId());

        try{
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("insert into comments (name, content, createdAt, newsId) values (?,?,?,?)", generatedColumns);

            preparedStatement.setString(1, comment.getName());
            preparedStatement.setString(2, comment.getContent());
            preparedStatement.setString(3, comment.getCreatedAt());
            preparedStatement.setInt(4,comment.getNewsId());


            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                comment.setId(resultSet.getInt(1));
            }


        }catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comment;
    }

    @Override
    public List<Comment> getAllComments() {
        List<Comment> allComments = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from comments");

            while (resultSet.next()){
                allComments.add(new Comment(resultSet.getInt("id"), resultSet.getString("name"),resultSet.getString("content"), resultSet.getString("createdAt"), resultSet.getInt("newsId")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return allComments;
    }

    @Override
    public Comment findCommentById(int id) {
        Comment comment = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM comments where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                comment = new Comment(resultSet.getInt("id"), resultSet.getString("name"),resultSet.getString("content"), resultSet.getString("createdAt"), resultSet.getInt("newsId"));
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

        return comment;
    }

    @Override
    public void deleteCommentById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM comments where id = ?");
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
