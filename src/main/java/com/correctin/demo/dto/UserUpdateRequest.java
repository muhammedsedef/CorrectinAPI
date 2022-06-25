package com.correctin.demo.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserUpdateRequest {

    @Size(min = 2, max = 155, message = "First Name at least 5 character and maxiumum 25 characters")
    @NotBlank(message = "first name is mandotory")
    private String firstName;

    @Size(min = 2, max = 155, message = "Last Name at least 5 character and maxiumum 25 characters")
    @NotBlank(message = "last name is mandotory")
    private String lastName;

    @Email(message = "Please enter valid email format")
    @Pattern(regexp=".+@.+\\.[a-z]+")
    private String email;

    private Long nativeLanguageId;

    private Long foreignLanguageId;
}
