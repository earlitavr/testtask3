package com.game.entity;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerSpecification implements Specification<PlayerEntity> {
    private List<SearchCriteria> criteriaList = new ArrayList<>();

    public void add(SearchCriteria criteria) {
        criteriaList.add(criteria);
    }

    public boolean isEmpty() {
        return criteriaList.size() == 0;
    }

    @Override
    public Predicate toPredicate(Root<PlayerEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        for(SearchCriteria criteria : criteriaList) {
            if(criteria.getOperation().equals(SearchOperation.EQUAL))
                predicates.add(criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue()));
            else if(criteria.getOperation().equals(SearchOperation.MATCH))
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"));
            else if(criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL))
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
            else if(criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL))
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
            else if(criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL_DATE))
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()), (Date)criteria.getValue()));
            else if(criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL_DATE))
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()), (Date)criteria.getValue()));

        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
