package com.correctin.demo.service;

import com.correctin.demo.dto.ChangePasswordRequest;
import com.correctin.demo.dto.CreateUserRequest;
import com.correctin.demo.dto.UserUpdateRequest;
import com.correctin.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User save(CreateUserRequest createUserRequest);

    User getById(Boolean status, Long id);

    Page<User> getAll(Pageable pageable);

    Page<User> findByFullName(Boolean status, String search, Pageable pageable);

    Boolean deleteUser(Long id);

    User updateUser(Long id, UserUpdateRequest userUpdateRequest);

    // return authenticated user
    User getUserDetails();

    User getUserByEmail(String email);

    Boolean changePassword(ChangePasswordRequest changePasswordRequest);
}
