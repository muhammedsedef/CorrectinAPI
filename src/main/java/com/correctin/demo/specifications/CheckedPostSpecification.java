package com.correctin.demo.specifications;

import com.correctin.demo.entity.CheckedPost;
import org.springframework.data.jpa.domain.Specification;

public class CheckedPostSpecification {
    public static Specification<CheckedPost> hasNativeLanguage(Long nativeLanguageId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("nativeLanguage"), nativeLanguageId);
    }
    public static Specification<CheckedPost> hasForeignLanguage(Long foreignLanguageId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("foreignLanguage"), foreignLanguageId);
    }
    public static Specification<CheckedPost> isUserId(Long userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user"), userId);
    }
}
