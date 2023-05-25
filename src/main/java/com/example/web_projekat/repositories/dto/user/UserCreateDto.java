package com.example.web_projekat.repositories.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserCreateDto {

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String firstName;

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String lastName;

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String email;

    @NotNull(message = "Field is required")
    private int roleId;

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String hashedPassword;

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String confirmPassword;

}
