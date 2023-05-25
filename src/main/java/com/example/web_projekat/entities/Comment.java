package com.example.web_projekat.entities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class Comment {

    private int id;

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String name;

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String content;

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String createdAt;

    @NotNull(message = "Field is required")
    private int newsId;

    public Comment() {
    }

    public Comment(String name, String content, String createdAt, int newsId) {
        this.name = name;
        this.content = content;
        this.createdAt = createdAt;
        this.newsId = newsId;
    }

    public Comment(int id, String name, String content, String createdAt, int newsId) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.createdAt = createdAt;
        this.newsId = newsId;
    }
}
