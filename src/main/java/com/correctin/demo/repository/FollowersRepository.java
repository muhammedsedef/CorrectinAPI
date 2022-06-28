package com.correctin.demo.repository;

import com.correctin.demo.entity.Followers;
import com.correctin.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowersRepository extends JpaRepository<Followers, Long> {

    // If a user want to follow again the same user.
    Optional<Followers> findByFromAndTo(User from, User to);

    Page<Followers> findByIsAcceptedAndFrom(Boolean isAccepted, User user, Pageable pageable);

    Page<Followers> findByIsAcceptedAndTo(Boolean isAccepted, User user, Pageable pageable);

    //Page<Followers> findByIsAcceptedAndFromOrTo(Boolean status, User fromUser,User toUser, Pageable pageable);
}
