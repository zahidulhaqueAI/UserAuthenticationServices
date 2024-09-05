package com.auth.service.userauthenticationservice.service;

import com.auth.service.userauthenticationservice.models.User;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.util.MultiValueMap;

public interface IAuthService {

    public User signup(String username, String password);
    public Pair<User, MultiValueMap<String,String>> login(String username, String password);

    public User logout(String email);
}
