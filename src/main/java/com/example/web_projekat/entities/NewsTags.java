package com.example.web_projekat.entities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NewsTags {

    @NotNull(message = "Field is required")
    private int newsId;

    @NotNull(message = "Field is required")
    private int tagsId;

    public NewsTags() {
    }

    public NewsTags(int newsId, int tagsId) {
        this.newsId = newsId;
        this.tagsId = tagsId;
    }
}
