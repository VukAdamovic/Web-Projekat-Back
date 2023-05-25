package com.example.web_projekat.entities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class News {

    private int id;

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String title;

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String content;

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String createdAt;

    @NotNull(message = "Field is required")
    private int visitNumber;

    @NotNull(message = "Field is required")
    private int categoryId;

    @NotNull(message = "Field is required")
    private int userId;

    public News() {
    }

    public News(String title, String content, String createdAt, int visitNumber, int categoryId, int userId) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.visitNumber = visitNumber;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    public News(int id, String title, String content, String createdAt, int visitNumber, int categoryId, int userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.visitNumber = visitNumber;
        this.categoryId = categoryId;
        this.userId = userId;
    }
}
