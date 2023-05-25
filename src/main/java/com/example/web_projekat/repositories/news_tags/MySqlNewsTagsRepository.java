package com.example.web_projekat.repositories.news_tags;

import com.example.web_projekat.entities.NewsTags;
import com.example.web_projekat.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlNewsTagsRepository extends MySqlAbstractRepository implements NewsTagsRepository {

    @Override
    public NewsTags createNews_Tags(NewsTags news_tags) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = this.newConnection();


            preparedStatement = connection.prepareStatement("insert into news_tags (newsId,tagsId) values (?,?)");

            preparedStatement.setInt(1, news_tags.getNewsId());
            preparedStatement.setInt(2, news_tags.getTagsId());

            preparedStatement.executeUpdate();




        }catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return news_tags;
    }

    @Override
    public List<NewsTags> getAllNewsTags() {
        List<NewsTags> allNewsTags = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from news_tags");

            while (resultSet.next()){
                allNewsTags.add(new NewsTags(resultSet.getInt("newsId"), resultSet.getInt("tagsId")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return allNewsTags;
    }

    @Override
    public List<NewsTags> findNewsTagsByNewsId(Integer news_id) {
        List<NewsTags> allNewsTags = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news_tags where newsId = ?");
            preparedStatement.setInt(1, news_id);
            resultSet = preparedStatement.executeQuery();



            while (resultSet.next()){
                allNewsTags.add(new NewsTags(resultSet.getInt("newsId"), resultSet.getInt("tagsId")));
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

        return allNewsTags;
    }

    @Override
    public List<NewsTags> findNewsTagsByTagId(Integer tag_id) {
        List<NewsTags> allNewsTags = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news_tags where tagsId = ?");
            preparedStatement.setInt(1, tag_id);
            resultSet = preparedStatement.executeQuery();



            while (resultSet.next()){
                allNewsTags.add(new NewsTags(resultSet.getInt("newsId"), resultSet.getInt("tagsId")));
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

        return allNewsTags;
    }

    @Override
    public void deleteNewsTags(Integer news_id, Integer tag_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM news_tags where newsId = ? and tagsId = ?");
            preparedStatement.setInt(1, news_id);
            preparedStatement.setInt(2, tag_id);
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
