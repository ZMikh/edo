package ru.mikhailova.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.mikhailova.domain.Task;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    @Override
    @EntityGraph(value = "Task.withEmployees")
    Page<Task> findAll(Specification<Task> spec, Pageable pageable);

    @Override
    @EntityGraph(value = "Task.withEmployees")
    Page<Task> findAll(Pageable pageable);

    @Override
    @EntityGraph(value = "Task.withEmployees")
    Optional<Task> findById(Long aLong);
}
