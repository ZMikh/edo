package ru.mikhailova.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mikhailova.statemachine.service.TaskStateService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task/state")
public class TaskStateController {
    private final TaskStateService taskStateService;

    @ApiOperation("Выполнение поручения")
    @PostMapping("/execute/{id}")
    public ResponseEntity<Void> execute(@PathVariable Long id) {
        boolean executed = taskStateService.executed(id);
        if (executed) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ApiOperation("Доработка поручения")
    @PostMapping("/rework/{id}")
    public ResponseEntity<Void> rework(@PathVariable Long id) {
        boolean reworked = taskStateService.reworked(id);
        if (reworked) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ApiOperation("Приемка поручения")
    @PostMapping("/accept/{id}")
    public ResponseEntity<Void> accepted(@PathVariable Long id) {
        boolean accepted = taskStateService.accepted(id);
        if (accepted) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
