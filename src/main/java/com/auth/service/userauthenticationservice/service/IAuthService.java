package com.auth.service.userauthenticationservice.service;

import com.auth.service.userauthenticationservice.models.User;

public interface IAuthService {

    public User signup(String username, String password);
    public User login(String username, String password);

    public User logout(String email);
}
