package com.correctin.demo.repository;

import com.correctin.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByStatusAndFirstName(Boolean status, String firstName);
    User findByStatusAndId(Boolean status, Long id);
    Page<User> findByStatus(Boolean status, Pageable pageable);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
