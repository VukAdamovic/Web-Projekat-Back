package com.example.web_projekat.services;

import com.example.web_projekat.entities.News;
import com.example.web_projekat.entities.Tags;
import com.example.web_projekat.repositories.dto.tags.TagsDto;
import com.example.web_projekat.repositories.tags.TagsRepository;

import javax.inject.Inject;
import java.util.List;

public class TagsService {

    @Inject
    private TagsRepository tagsRepository;

    public Tags createTag(TagsDto tagsDto){
        return tagsRepository.createTag(tagsDto);
    }

    public List<Tags> getAllTags(){
        return tagsRepository.getAllTags();
    }

    public Tags findTagById(int id){
        return tagsRepository.findTagById(id);
    }

    public Tags updateTag(int id, TagsDto tagsDto){
        return tagsRepository.updateTag(id, tagsDto);
    }

    public List<News> filterByTag(int tagId, int page){
        return tagsRepository.filterByTag(tagId,page);
    }

    public void deleteTagById(int id){
        tagsRepository.deleteTagById(id);
    }
}
