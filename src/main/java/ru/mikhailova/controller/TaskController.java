package ru.mikhailova.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.mikhailova.domain.Task;
import ru.mikhailova.dto.TaskDto;
import ru.mikhailova.dto.TaskRequestCreateDto;
import ru.mikhailova.dto.TaskRequestUpdateDto;
import ru.mikhailova.dto.TaskResponseCreateDto;
import ru.mikhailova.mapper.TaskMapper;
import ru.mikhailova.service.TaskService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService service;
    private final TaskMapper mapper;

    @ApiOperation("Просмотр поручения по идентификатору")
    @GetMapping("/get-by-id/{id}")
    public TaskDto getById(@PathVariable Long id) {
        Task task = service.getTaskById(id);
        return mapper.toDto(task);
    }

    @ApiOperation("Создание поручения")
    @PostMapping("/create")
    public TaskResponseCreateDto create(@Valid @RequestBody TaskRequestCreateDto taskRequestCreateDto) {
        Task task = service.createTask(mapper.toRequestCreateEntity(taskRequestCreateDto),
                taskRequestCreateDto.getAuthorId(), taskRequestCreateDto.getExecutorsId());
        return mapper.toResponseCreateDto(task);
    }

    @ApiOperation("Удаление поручения")
    @DeleteMapping("/delete-by-id/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteTask(id);
    }

    @ApiOperation("Редактирование поручения")
    @PutMapping("/update/{id}")
    public TaskDto update(@PathVariable Long id,
                          @RequestBody TaskRequestUpdateDto taskRequestUpdateDto) {
        return mapper.toDto(service.updateTask(id, mapper.toUpdateParam(taskRequestUpdateDto)));
    }

    @ApiOperation("Просмотр поручений по атрибутам с постраничной навигацией")
    @GetMapping("/get-by-query")
    public List<TaskDto> getByQuery(@RequestParam(required = false) Integer pageNumber,
                                    @RequestParam(required = false) Integer pageSize,
                                    @RequestParam(required = false) Boolean isExecuted,
                                    @RequestParam(required = false) Boolean isControlled,
                                    @RequestParam(required = false) String subject,
                                    @RequestParam(required = false) Long authorId) {
        Page<Task> allByQuery = service.getAllTasksByQuery(pageNumber, pageSize, isExecuted,
                isControlled, subject, authorId);
        return allByQuery.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
