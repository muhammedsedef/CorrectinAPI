package com.correctin.demo.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
    @Email(message = "Please enter valid email")
    private String email;

    @Size(min = 5, max = 25, message = "Password at least 5 character and maxiumum 25 characters")
    private String password;
}
