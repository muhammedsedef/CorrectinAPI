package com.correctin.demo.dto;

import com.correctin.demo.entity.Language;
import com.correctin.demo.util.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @Size(min = 2, max = 155, message = "First Name at least 5 character and maxiumum 25 characters")
    @NotBlank(message = "first name is mandotory")
    private String firstName;

    @Size(min = 2, max = 155, message = "Last Name at least 5 character and maxiumum 25 characters")
    @NotBlank(message = "last name is mandotory")
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    @UniqueEmail
    @Email(message = "Please enter valid email format")
    @Pattern(regexp=".+@.+\\.[a-z]+")
    private String email;

    @Size(min = 5, max = 25, message = "Password at least 5 character and maxiumum 25 characters")
    private String password;

    private Long nativeLanguage;

    private Long foreignLanguage;

}
