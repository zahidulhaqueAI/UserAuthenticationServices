package com.auth.service.userauthenticationservice.service;

import com.auth.service.userauthenticationservice.models.User;
import com.auth.service.userauthenticationservice.repos.UserRepo;
import io.jsonwebtoken.Jwts;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    AuthService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User signup(String email, String password) {
        Optional<User> userByEmail = userRepo.findUserByEmail(email);

        if(userByEmail.isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        return userRepo.save(user);
    }

    @Override
    public Pair<User, MultiValueMap<String,String>> login(String email, String password) {

        Optional<User> userByEmail = userRepo.findUserByEmail(email);
        if(userByEmail.isPresent()) {
            User user = userByEmail.get();

            // if password entered matches
            if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
                throw  new RuntimeException("Wrong password");
            }

            // Hard coded Payload
            String message = "{\n" +
                "   \"email\": \"zahid@gmail.com\",\n" +
                "   \"roles\": [\n" +
                "      \"instructor\",\n" +
                "      \"buddy\"\n" +
                "   ],\n" +
                "   \"expirationDate\": \"25thSept2024\"\n" +
                "}";

            // Token generation
            byte[] contents = message.getBytes(StandardCharsets.UTF_8);
            String token = Jwts.builder().content(contents).compact();

            // set the token to the header
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add(HttpHeaders.SET_COOKIE, token);

            // make pair
            Pair<User, MultiValueMap<String,String>> p = new Pair<>(user, headers);
            return p;
        }

        return null;
    }

    @Override
    public User logout(String email) {
        return null;
    }
}
