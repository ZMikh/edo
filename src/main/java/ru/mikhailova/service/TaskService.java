package ru.mikhailova.service;

import org.springframework.data.domain.Page;
import ru.mikhailova.domain.Task;

import java.util.List;

/**
 * Сервис Поручение
 */
public interface TaskService { //TODO Java Doc
    /**
     * Создать поручение
     * @param task
     * @return сущность Поручение
     */
    Task createTask(Task task, Long authorId, List<Long> executorsId);
    /**
     * Получить поручение по идентификатору поручения
     * @param id
     * @return сущность Поручение
     */
    Task getTaskById(Long id);
    /**
     * Удалить поручение
     * @param id
     */
    void deleteTask(Long id);
    /**
     * Обновить поручение
     * @param id
     * @param updateParam
     * @return сущность Поручение
     */
    Task updateTask(Long id, UpdateParam updateParam);

    Page<Task> getAllTasksByQuery(Integer pageNumber,
                                  Integer pageSize,
                                  Boolean isExecuted,
                                  Boolean isControlled,
                                  String taskName,
                                  Long authorId);
}
