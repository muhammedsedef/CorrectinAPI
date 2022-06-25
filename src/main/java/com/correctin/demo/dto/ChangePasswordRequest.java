package com.correctin.demo.dto;

import com.correctin.demo.util.UniqueEmail;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordRequest {

    @Column(name = "email", unique = true, nullable = false)
    @UniqueEmail
    @Email(message = "Please enter valid email format")
    @Pattern(regexp=".+@.+\\.[a-z]+")
    private String email;

    @Size(min = 5, max = 25, message = "Password at least 5 character and maxiumum 25 characters")
    private String oldPassword;

    @Size(min = 5, max = 25, message = "Password at least 5 character and maxiumum 25 characters")
    private String newPassword;
}
