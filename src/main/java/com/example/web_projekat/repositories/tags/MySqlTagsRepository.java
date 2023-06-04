package com.example.web_projekat.repositories.tags;

import com.example.web_projekat.entities.News;
import com.example.web_projekat.entities.NewsTags;
import com.example.web_projekat.entities.Tags;
import com.example.web_projekat.repositories.MySqlAbstractRepository;
import com.example.web_projekat.repositories.dto.tags.TagsDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MySqlTagsRepository extends MySqlAbstractRepository implements TagsRepository {

    @Override
    public Tags createTag(TagsDto tagsDto) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Tags tag = new Tags();
        tag.setKeyWord(tagsDto.getKeyWord());


        try{
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("insert into tags (keyWord) values (?)", generatedColumns);

            preparedStatement.setString(1, tag.getKeyWord());

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                tag.setId(resultSet.getInt(1));
            }


        }catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return tag;
    }

    @Override
    public List<Tags> getAllTags() {
        List<Tags> allTags = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from tags");

            while (resultSet.next()){
                allTags.add(new Tags(resultSet.getInt("id"), resultSet.getString("keyWord")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return allTags;
    }

    @Override
    public Tags findTagById(int id) {
        Tags tag = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM tags where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();



            while (resultSet.next()){
                tag = new Tags(resultSet.getInt("id"), resultSet.getString("keyWord"));
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

        return tag;
    }

    @Override
    public Tags updateTag(int id, TagsDto tagsDto) {
        Tags tag = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE tags SET keyWord = ? WHERE id = ?");
            preparedStatement.setString(1, tagsDto.getKeyWord());
            preparedStatement.setInt(2, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                String selectQuery = "SELECT id, keyWord FROM tags WHERE id = ?";
                preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    tag = new Tags(resultSet.getInt("id"), resultSet.getString("keyWord"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResultSet(resultSet);
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return tag;
    }

    @Override
    public List<News> filterByTag(int tagId, int page) {
        List<News> allNews = new ArrayList<>();
        List<NewsTags> newsId = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int itemsPerPage = 10;
        int startIndex = (page - 1) * itemsPerPage;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news_tags WHERE tagsId = ?");
            preparedStatement.setInt(1, tagId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                newsId.add(new NewsTags(resultSet.getInt("newsId"), resultSet.getInt("tagsId")));
            }

            // Promenite petlju za dobavljanje vesti kako biste ograničili broj vesti na strani
            for (int i = startIndex; i < Math.min(startIndex + itemsPerPage, newsId.size()); i++) {
                NewsTags newsTags = newsId.get(i);

                preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE id = ?");
                preparedStatement.setInt(1, newsTags.getNewsId());
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    allNews.add(new News(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("content"),
                            resultSet.getString("createdAt"), resultSet.getInt("visitNumber"), resultSet.getInt("categoryId"), resultSet.getInt("userId")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Oslobađanje resursa
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            closeConnection(connection);
        }

        return allNews;
    }


    @Override
    public void deleteTagById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM news_tags where tagsId = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            preparedStatement = connection.prepareStatement("DELETE FROM tags where id = ?");
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
