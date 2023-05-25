package com.example.web_projekat.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.web_projekat.entities.User;
import com.example.web_projekat.repositories.dto.user.UserCreateDto;
import com.example.web_projekat.repositories.dto.user.UserUpdateDto;
import com.example.web_projekat.repositories.user.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;
import java.util.List;

public class UserService {

    @Inject
    private UserRepository userRepository;

    public User createUser(UserCreateDto userCreateDto){
        return userRepository.createUser(userCreateDto);
    }

    public List<User> getAllUser(){
        return userRepository.getAllUser();
    }

    public User findUserById(int id){
        return userRepository.findUserById(id);
    }

    public User updateUser(int id, UserUpdateDto userUpdateDto){
        return userRepository.updateUser(id, userUpdateDto);
    }

    public User changeActivation(int id) {
       return userRepository.changeActivation(id);
    }

    public void deleteUserById(int id){
        userRepository.deleteUserById(id);
    }

    //kreiranje JWT-a
    public String login(String email, String password) {
        String hashedPassword = DigestUtils.sha256Hex(password);

        User user = userRepository.findUserByEmailAndPassword(email,hashedPassword);
        if (user == null || !user.getStatus()) {
            return null;
        }

        Algorithm algorithm = Algorithm.HMAC256("secret");

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("id",user.getId())
                .withClaim("roleId",user.getRoleId())
                .sign(algorithm);
    }

    //provera jwt-a
    public boolean isAuthorized(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        int idUser = jwt.getClaim("id").asInt();

        User user = this.userRepository.findUserById(idUser);


        if (user == null){
            return false;
        }

        return true;
    }
}
