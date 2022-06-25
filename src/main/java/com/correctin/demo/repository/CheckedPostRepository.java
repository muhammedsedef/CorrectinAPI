package com.correctin.demo.repository;

import com.correctin.demo.entity.CheckedPost;
import com.correctin.demo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckedPostRepository extends JpaRepository<CheckedPost, Long>, JpaSpecificationExecutor<CheckedPost> {
    CheckedPost findByOldPostId(Long oldPostId);

    Page<CheckedPost> findByStatus(Boolean status, Pageable pageable);
}
