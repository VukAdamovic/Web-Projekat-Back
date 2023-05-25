package com.example.web_projekat.repositories.user;

import com.example.web_projekat.entities.User;
import com.example.web_projekat.repositories.dto.user.UserCreateDto;
import com.example.web_projekat.repositories.dto.user.UserUpdateDto;

import java.util.List;

public interface UserRepository {

    User createUser(UserCreateDto userCreateDto);

    List<User> getAllUser();

    User findUserById(int id);

    User findUserByEmailAndPassword(String email, String hashedPassword);

    User updateUser(int id, UserUpdateDto userUpdateDto);

    User changeActivation(int id);

    void deleteUserById(int id);
}
