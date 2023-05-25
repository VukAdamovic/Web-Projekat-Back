package com.example.web_projekat.repositories.tags;

import com.example.web_projekat.entities.Tags;
import com.example.web_projekat.repositories.dto.tags.TagsDto;

import java.util.List;

public interface TagsRepository {

    Tags createTag(TagsDto tagsDto);

    List<Tags> getAllTags();

    Tags findTagById(int id);

    Tags updateTag(int id, TagsDto tagsDto);

    void deleteTagById(int id);

}
