package com.correctin.demo.specifications;

import com.correctin.demo.dto.PostFilterParam;
import com.correctin.demo.entity.Post;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class PostSpecification {

    public static Specification<Post> getFilteredPosts(PostFilterParam params){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            String sortBy = "createdAt";
            if(params.getStatus() != null)
                predicates.add(criteriaBuilder.equal(root.get("status"), params.getStatus()));
            if(params.getIsChecked() != null)
                predicates.add(criteriaBuilder.equal(root.get("isChecked"), params.getIsChecked()));
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
