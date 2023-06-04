package com.example.web_projekat.services;

import com.example.web_projekat.entities.News;
import com.example.web_projekat.repositories.dto.news.NewsDto;
import com.example.web_projekat.repositories.news.NewsRepository;

import javax.inject.Inject;
import java.util.List;

public class NewsService {

    @Inject
    private NewsRepository newsRepository;

    public News createNews(int userId, NewsDto newsDto){
        return newsRepository.createNews(userId, newsDto);
    }

    public List<News> getAllNews(int page){
        return newsRepository.getAllNews(page);
    }

    public News findNewsById(int id){
        return newsRepository.findNewsById(id);
    }

    public News updateNews(int id, NewsDto newsDto){
        return newsRepository.updateNews(id,newsDto);
    }

    public List<News> getTopStories(){
        return newsRepository.getTopStories();
    }

    public void deleteNewsById(int id){
        newsRepository.deleteNewsById(id);
    }
}
