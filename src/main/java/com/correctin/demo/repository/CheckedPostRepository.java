package com.correctin.demo.repository;

import com.correctin.demo.entity.CheckedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckedPostRepository extends JpaRepository<CheckedPost, Long> {
    CheckedPost findByOldPostId(Long oldPostId);
}
