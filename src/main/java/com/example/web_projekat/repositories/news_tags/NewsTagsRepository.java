package com.example.web_projekat.repositories.news_tags;

import com.example.web_projekat.entities.NewsTags;

import java.util.List;

public interface NewsTagsRepository {

    NewsTags createNews_Tags(NewsTags news_tags);

    List<NewsTags> getAllNewsTags();

    List<NewsTags> findNewsTagsByNewsId(Integer news_id);

    List<NewsTags> findNewsTagsByTagId(Integer tag_id);

    void deleteNewsTags(Integer news_id, Integer tag_id);

}
