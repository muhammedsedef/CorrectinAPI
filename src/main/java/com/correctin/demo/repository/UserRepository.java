package com.correctin.demo.repository;

import com.correctin.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByStatusAndId(Boolean status, Long id);
    Optional<User> findByEmail(String email);
    Page<User> findByStatusAndFullNameContaining(Boolean status, String search, Pageable page);
}
