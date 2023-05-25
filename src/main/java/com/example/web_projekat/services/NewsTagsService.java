package com.example.web_projekat.services;

import com.example.web_projekat.entities.NewsTags;
import com.example.web_projekat.repositories.news_tags.NewsTagsRepository;

import javax.inject.Inject;
import java.util.List;

public class NewsTagsService {

    @Inject
    private NewsTagsRepository newsTagsRepository;

    public NewsTags createNews_Tags(NewsTags news_tags){
        return newsTagsRepository.createNews_Tags(news_tags);
    }

    public List<NewsTags> getAllNewsTags(){
        return newsTagsRepository.getAllNewsTags();
    }

    public List<NewsTags> findNewsTagsByNewsId(Integer news_id){
        return newsTagsRepository.findNewsTagsByNewsId(news_id);
    }

    public List<NewsTags> findNewsTagsByTagId(Integer tag_id){
        return newsTagsRepository.findNewsTagsByTagId(tag_id);
    }

    public void deleteNewsTags(Integer news_id, Integer tag_id){
        newsTagsRepository.deleteNewsTags(news_id, tag_id);
    }
}
