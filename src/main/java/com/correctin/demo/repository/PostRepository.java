package com.correctin.demo.repository;

import com.correctin.demo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;



@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    Post findByStatusAndId(Boolean status, Long id);
    Page<Post> findByStatusAndUserId(Boolean status,Long userId, Pageable pageable);
    Page<Post> findByStatusAndUser_NativeLanguageIdAndUser_ForeignLanguageId(Boolean status,
                                                                         Long nativeLanguageId,
                                                                         Long foreignLanguageId,
                                                                         Pageable pageable);
    Page<Post> findByStatus(Boolean status, Pageable pageable);

}
