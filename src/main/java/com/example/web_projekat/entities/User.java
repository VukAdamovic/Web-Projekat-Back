package com.example.web_projekat.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class User {

    private int id;

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String email;

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String firstName;

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String lastName;

    @NotNull(message = "Field is required")
    private int roleId;

    @NotNull(message = "Field is required")
    private Boolean status;

    @NotNull(message = "Field is required")
    @NotEmpty(message = "Field is required")
    private String hashedPassword;

    public User() {
    }

    public User(String email, String firstName, String lastName, int roleId, String hashedPassword) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
        this.hashedPassword = hashedPassword;
    }

    public User(String email, String firstName, String lastName, int roleId) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
    }

    public User(int id, String email, String firstName, String lastName, int roleId, Boolean status, String hashedPassword) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
        this.status = status;
        this.hashedPassword = hashedPassword;
    }
}
