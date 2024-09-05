package com.auth.service.userauthenticationservice.controller;

import com.auth.service.userauthenticationservice.dto.LoginRequestDto;
import com.auth.service.userauthenticationservice.dto.LogoutRequestDto;
import com.auth.service.userauthenticationservice.dto.SignupRequestDto;
import com.auth.service.userauthenticationservice.dto.UserDto;
import com.auth.service.userauthenticationservice.models.User;
import com.auth.service.userauthenticationservice.service.IAuthService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

    AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    private ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        try {
            if (signupRequestDto.getEmail() == null || signupRequestDto.getPassword() == null || signupRequestDto.getPassword().length() < 6) {
                //    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                throw new RuntimeException("Provided credential is not as per rules");
            }

            User signupUser = authService.signup(signupRequestDto.getEmail(), signupRequestDto.getPassword());
            return new ResponseEntity<>(from(signupUser), HttpStatus.CREATED);
        }catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) {

        try {

            Pair<User, MultiValueMap<String,String>> loginUser = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            return new ResponseEntity<>(from(loginUser.a), loginUser.b, HttpStatus.OK);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<UserDto> logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        return null;
    }

    private UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRole());
        return userDto;
    }
}
