package ru.mikhailova.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.mikhailova.domain.Task;
import ru.mikhailova.dto.TaskDto;
import ru.mikhailova.dto.TaskRequestCreateDto;
import ru.mikhailova.dto.TaskRequestUpdateDto;
import ru.mikhailova.dto.TaskResponseCreateDto;
import ru.mikhailova.service.UpdateParam;

import static ru.mikhailova.domain.statemachine.TaskState.NEW;

@Component
public class TaskMapper {
    private final ModelMapper mapper;

    public TaskMapper() {
        this.mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.typeMap(TaskRequestCreateDto.class, Task.class)
                .addMapping(dto -> false, Task::setIsControlled)
                .addMapping(dto -> false, Task::setIsExecuted)
                .addMapping(dto -> NEW, Task::setTaskState);
    }

    public TaskDto toDto(Task task) {
        return mapper.map(task, TaskDto.class);
    }

    public Task toRequestCreateEntity(TaskRequestCreateDto taskRequestCreateDto) {
        return mapper.map(taskRequestCreateDto, Task.class);
    }

    public TaskResponseCreateDto toResponseCreateDto(Task task) {
        return mapper.map(task, TaskResponseCreateDto.class);
    }

    public UpdateParam toUpdateParam(TaskRequestUpdateDto dto) {
        return mapper.map(dto, UpdateParam.class);
    }
}
