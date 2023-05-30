package com.example.web_projekat.repositories.news;

import com.example.web_projekat.entities.News;
import com.example.web_projekat.repositories.dto.news.NewsDto;

import java.util.List;

public interface NewsRepository {

    News createNews(int userId, NewsDto newsDto);

    List<News> getAllNews(int page);

    News findNewsById(int id);

    News updateNews(int id, NewsDto newsDto);

    void deleteNewsById(int id);
}
