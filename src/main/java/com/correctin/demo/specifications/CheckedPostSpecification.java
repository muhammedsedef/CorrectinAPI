package com.correctin.demo.specifications;

import com.correctin.demo.dto.CheckedPostFilterParam;
import com.correctin.demo.entity.CheckedPost;
import com.correctin.demo.entity.Post;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CheckedPostSpecification {

    public static Specification<CheckedPost> getFilteredCheckedPosts(CheckedPostFilterParam params){
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            String sortBy = "createdAt";

            if(params.getStatus() != null)
                predicates.add(criteriaBuilder.equal(root.get("status"), params.getStatus()));
            if(params.getId() != null)
                predicates.add(criteriaBuilder.equal(root.get("id"), params.getId()));
            if(params.getOldPostId() != null)
                predicates.add(criteriaBuilder.equal(root.get("oldPost").get("id"), params.getOldPostId()));
            if(params.getOldPostUserId() != null)
                predicates.add(criteriaBuilder.equal(root.get("oldPost").get("user").get("id"), params.getOldPostUserId()));
            if(params.getNativeLanguageId() != null)
                predicates.add(criteriaBuilder.equal(root.get("user").get("nativeLanguage").get("id"), params.getNativeLanguageId()));
            if(params.getForeignLanguageId() != null)
                predicates.add(criteriaBuilder.equal(root.get("user").get("foreignLanguage").get("id"), params.getForeignLanguageId()));
            if(params.getUserId() != null)
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), params.getUserId()));
            if(params.getSortBy() != null)
                sortBy = params.getSortBy();
            if(params.getOrderBy()!= null && params.getOrderBy().equals("desc")){
                query.orderBy(criteriaBuilder.desc(root.get(sortBy)));
            }
            else{
                query.orderBy(criteriaBuilder.asc(root.get(sortBy)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
