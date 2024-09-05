package com.auth.service.userauthenticationservice.service;

import com.auth.service.userauthenticationservice.models.User;
import com.auth.service.userauthenticationservice.repos.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public User login(String email, String password) {

        Optional<User> userByEmail = userRepo.findUserByEmail(email);
        if(userByEmail.isPresent()) {
            User user = userByEmail.get();

            // if password entered matches
            if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
                throw  new RuntimeException("Wrong password");
            }
            return user;
        }

        return null;
    }

    @Override
    public User logout(String email) {
        return null;
    }
}
