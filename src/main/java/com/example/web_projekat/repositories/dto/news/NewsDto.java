package com.example.web_projekat.repositories.dto.news;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NewsDto {

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String title;

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String content;

    @NotNull(message = "Field is required")
    private int categoryId;

    public NewsDto() {
    }

    public NewsDto(String title, String content, int categoryId) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
    }
}
