package ru.mikhailova.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mikhailova.statemachine.service.TaskStateService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task/task-state")
public class TaskStateController {

    private final TaskStateService taskStateService;

    @ApiOperation("Проверка выполнения поручения")
    @PostMapping("/execute/{id}")
    public boolean execute(@PathVariable Long id) {
        return taskStateService.executed(id);
    }

    @ApiOperation("Проверка доработки поручения")
    @PostMapping("/rework/{id}")
    public boolean rework(@PathVariable Long id) {
        return taskStateService.reworked(id);
    }

    @ApiOperation("Проверка приемки поручения")
    @PostMapping("/accept/{id}")
    public boolean accepted(@PathVariable Long id) {
        return taskStateService.accepted(id);
    }
}
