package com.correctin.demo.entity;

import com.correctin.demo.util.UniqueEmail;
import com.correctin.demo.util.UniqueEmailValidator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "users")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @Size(min = 2, max = 155)
    @NotBlank(message = "first name is mandotory")
    //@Field(store = Store.YES)
    //@Field(name = "fullName") // this will be mapped to firstName
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 2, max = 155)
    @NotBlank(message = "last name is mandotory")
    //@Field(store = Store.YES)
    //@Field(name = "fullName") // this will be mapped to lastName
    private String lastName;

    @Formula ("CONCAT_WS( ' ', first_name, last_name ) " )
    private String fullName;

    @Column(name = "email", unique = true, nullable = false)
    @UniqueEmail
    @Email(message = "Please enter valid email format")
    @Pattern(regexp=".+@.+\\.[a-z]+")
    private String email;

    @Size(min = 5, max = 25)
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "native_language_id")
    private Language nativeLanguage;

    @ManyToOne
    @JoinColumn(name = "foreign_language_id")
    private Language foreignLanguage;

}
