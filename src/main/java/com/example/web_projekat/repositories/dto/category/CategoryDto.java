package com.example.web_projekat.repositories.dto.category;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoryDto {

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String name;

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String description;
}
