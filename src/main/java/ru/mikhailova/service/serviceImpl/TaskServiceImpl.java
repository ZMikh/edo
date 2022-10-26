package ru.mikhailova.service.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mikhailova.domain.Employee;
import ru.mikhailova.domain.Task;
import ru.mikhailova.repository.EmployeeRepository;
import ru.mikhailova.repository.TaskRepository;
import ru.mikhailova.service.TaskService;
import ru.mikhailova.service.UpdateParam;

import java.util.ArrayList;
import java.util.List;

import static ru.mikhailova.repository.TaskSpecs.*;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final Integer defaultPageNumber;
    private final Integer defaultPageSize;

    public TaskServiceImpl(TaskRepository taskRepository,
                           EmployeeRepository employeeRepository,
                           @Value(value = "${settings.default-page-number}") Integer defaultPageNumber,
                           @Value(value = "${settings.default-page-size}") Integer defaultPageSize) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.defaultPageNumber = defaultPageNumber;
        this.defaultPageSize = defaultPageSize;
    }

    @Transactional
    @Override
    public Task createTask(Task task, Long authorId, List<Long> executorsId) {
        Employee author = employeeRepository.findById(authorId).orElseThrow();
        task.setAuthor(author);
        List<Employee> executors = employeeRepository.findAllById(executorsId);
        task.setExecutors(executors);
        log.info("New task from author with id: {} is added", authorId);
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    @Override
    public Task getTaskById(Long id) {
        log.info("Task with id: {} is found", id);
        return taskRepository.findById(id).orElseThrow();
    }

    @Transactional
    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
        log.info("Task with id: {} is deleted", id);
    }

    @Transactional
    @Override
    public Task updateTask(Long id, UpdateParam updateParam) {
        Task task = taskRepository.findById(id).orElseThrow();
        if (updateParam.getIsExecuted() != null) {
            task.setIsExecuted(updateParam.getIsExecuted());
        }
        if (updateParam.getIsControlled() != null) {
            task.setIsControlled(updateParam.getIsControlled());
        }
        if (updateParam.getTerms() != null) {
            task.setTerms(updateParam.getTerms());
        }
        if (updateParam.getText() != null) {
            task.setText(updateParam.getText());
        }
        if (updateParam.getTaskState() != null) {
            task.setTaskState(updateParam.getTaskState());
        }
        taskRepository.save(task);
        log.info("Task with id: {} is updated", task.getId());
        return task;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Task> getAllTasksByQuery(Integer pageNumber,
                                         Integer pageSize,
                                         Boolean isExecuted,
                                         Boolean isControlled,
                                         String subject,
                                         Long authorId) {
        List<Specification<Task>> specificationList = getSpecifications(isExecuted, isControlled, subject, authorId);
        PageRequest pageRequest = PageRequest.of(pageNumber == null || pageNumber < 0 ? defaultPageNumber : pageNumber,
                pageSize == null || pageSize < 0 ? defaultPageSize : pageSize);
        if (specificationList.isEmpty()) {
            return taskRepository.findAll(pageRequest);
        }
        log.info("Task by following query: subject={}, authorId={}, isExecuted={}, isControlled={} is found",
                subject, authorId, isExecuted, isControlled);
        return taskRepository.findAll(joinSpecification(specificationList), pageRequest);
    }

    private List<Specification<Task>> getSpecifications(Boolean isExecuted,
                                                        Boolean isControlled,
                                                        String subject,
                                                        Long authorId) {
        List<Specification<Task>> specificationList = new ArrayList<>();
        if (isExecuted != null) {
            specificationList.add(isExecuted(isExecuted));
        }
        if (isControlled != null) {
            specificationList.add(isControlled(isControlled));
        }
        if (subject != null) {
            specificationList.add(hasName(subject));
        }
        if (authorId != null) {
            specificationList.add(hasAuthor(authorId));
        }
        return specificationList;
    }

    private Specification<Task> joinSpecification(List<Specification<Task>> specificationList) {
        Specification<Task> specification = specificationList.get(0);
        for (int i = 1; i < specificationList.size(); i++) {
            specification = specification.and(specificationList.get(i));
        }
        return specification;
    }
}
