package ru.mikhailova.repository;

import org.springframework.data.jpa.domain.Specification;
import ru.mikhailova.domain.Task;

public class TaskSpecs {
    public static Specification<Task> isExecuted(boolean state) {
        return (root, query, builder) -> builder.equal(root.get("isExecuted"), state);
    }

    public static Specification<Task> isControlled(boolean state) {
        return (root, query, builder) -> builder.equal(root.get("isControlled"), state);
    }

    public static Specification<Task> hasName(String name) {
        return (root, query, builder) -> builder.equal(root.get("subject"), name);
    }

    public static Specification<Task> hasAuthor(Long id) {
        return (root, query, builder) -> builder.equal(root.get("author").get("id"), id);
    }
}
