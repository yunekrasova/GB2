package com.geekbrains.server.repositories;

import com.geekbrains.gwt.common.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecifications {
    public static Specification<Task> captionContains(String word) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.upper(root.get("caption")), "%" + word.toUpperCase() + "%");
    }

    public static Specification<Task> statusEq(Task.Status status) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Task> assignedContains(String word) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.upper(root.get("assigned")), "%" + word.toUpperCase() + "%");
    }

    public static Specification<Task> idEq(Long id) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Task> ownerContains(String word) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("owner")), "%" + word.toUpperCase() + "%");

    }

    public static Specification<Task> descriptionContains(String word) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.upper(root.get("description")), "%" + word.toUpperCase() + "%");
    }
}
