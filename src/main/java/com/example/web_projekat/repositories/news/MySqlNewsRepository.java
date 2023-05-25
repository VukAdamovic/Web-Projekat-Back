package com.example.web_projekat.repositories.news;

import com.example.web_projekat.entities.News;
import com.example.web_projekat.repositories.MySqlAbstractRepository;
import com.example.web_projekat.repositories.dto.news.NewsDto;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MySqlNewsRepository extends MySqlAbstractRepository implements NewsRepository {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");

    @Override
    public News createNews(int userId, NewsDto newsDto) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        News news = new News(newsDto.getTitle(),newsDto.getContent(), LocalDate.now().format(DATE_FORMATTER),
                0, newsDto.getCategoryId(), userId);

        try{
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("insert into news (title, content, createdAt, visitNumber, categoryId, userId) values (?,?,?,?,?,?)", generatedColumns);

            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getContent());
            preparedStatement.setString(3, news.getCreatedAt());
            preparedStatement.setInt(4, news.getVisitNumber());
            preparedStatement.setInt(5, news.getCategoryId());
            preparedStatement.setInt(6, news.getUserId());

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                news.setId(resultSet.getInt(1));
            }


        }catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public List<News> getAllNews() {
        List<News> allNews = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from news");

            while (resultSet.next()){
                allNews.add(new News(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("content"),
                        resultSet.getString("createdAt"), resultSet.getInt("visitNumber"), resultSet.getInt("categoryId"), resultSet.getInt("userId")));}

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return allNews;
    }

    @Override
    public News findNewsById(int id) {
        News news = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();



            while (resultSet.next()){
                news = new News(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("content"),
                        resultSet.getString("createdAt"), resultSet.getInt("visitNumber") + 1, resultSet.getInt("categoryId"), resultSet.getInt("userId"));

            }

            preparedStatement = connection.prepareStatement("UPDATE news SET visitNumber = ? WHERE id = ?");
            preparedStatement.setInt(1, news.getVisitNumber());
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();


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

        return news;
    }

    @Override
    public News updateNews(int id, NewsDto newsDto) {
        News news = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE news SET title = ?, content = ?, categoryId = ? WHERE id = ?");
            preparedStatement.setString(1, newsDto.getTitle());
            preparedStatement.setString(2, newsDto.getContent());
            preparedStatement.setInt(3, newsDto.getCategoryId());
            preparedStatement.setInt(4, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                String selectQuery = "SELECT id, title, content, createdAt, visitNumber, categoryId, userId FROM news WHERE id = ?";
                preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    news = new News(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("content"),
                            resultSet.getString("createdAt"), resultSet.getInt("visitNumber"), resultSet.getInt("categoryId"), resultSet.getInt("userId"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResultSet(resultSet);
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public void deleteNewsById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM news where id = ?");
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