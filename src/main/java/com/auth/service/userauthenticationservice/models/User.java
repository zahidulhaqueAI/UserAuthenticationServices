package com.auth.service.userauthenticationservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class User extends BaseModel{

    private String email;
    private String password;

    @ManyToMany
    // If we don't give new HashSet, role will have null value;
    // using new will have empty set. Good to have new
    private Set<Role> role = new HashSet<>();
}
