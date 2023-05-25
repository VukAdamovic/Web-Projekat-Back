package com.example.web_projekat.entities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Tags {

    private int id;

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String keyWord;

    public Tags() {
    }

    public Tags(int id, String keyWord) {
        this.id = id;
        this.keyWord = keyWord;
    }
}
