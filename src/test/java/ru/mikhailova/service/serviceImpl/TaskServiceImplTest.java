package ru.mikhailova.service.serviceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mikhailova.domain.Task;
import ru.mikhailova.repository.EmployeeRepository;
import ru.mikhailova.repository.TaskRepository;
import ru.mikhailova.service.UpdateParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private EmployeeRepository employeeRepository;

    private TaskServiceImpl service;

    @Captor
    private ArgumentCaptor<Task> argumentCaptor;

    @BeforeEach
    void setUp() {
        service = new TaskServiceImpl(taskRepository, employeeRepository, 0, 10);
    }

    @Test
    void shouldCreateTask() {
        service.createTask(addTask(), 1L, List.of(1L));

        verify(taskRepository, times(1)).save(any());
    }

    @Test
    void shouldGetTask() {
        Long id = 1L;
        given(taskRepository.findById(id)).willReturn(Optional.ofNullable(addTask()));
        Task task = service.getTaskById(id);

        assertThat(id).isEqualTo(task.getId());
        //TODO get test
    }


    @Test
    void shouldDeleteTask() {
        Long id = 1L;
        service.deleteTask(id);

        verify(taskRepository, times(1)).deleteById(any());
    }

    @Test
    void shouldUpdateTask() {
        UpdateParam updatedTask = UpdateParam.builder()
                .terms(LocalDateTime.now())
                .isControlled(Boolean.TRUE)
                .isExecuted(Boolean.TRUE)
                .text("InfoValidation")
                .build();

        when(taskRepository.findById(any())).thenReturn(Optional.of(new Task()));

        service.updateTask(1L, updatedTask);

        verify(taskRepository, times(1)).save(argumentCaptor.capture());

        Task capturedTask = argumentCaptor.getValue();
        assertThat(capturedTask.getText()).isEqualTo(updatedTask.getText());
    }

    Task addTask() {
        return Task.builder()
                .id(1l)
                .subject("Task100")
                .isExecuted(false)
                .isControlled(false)
                .text("Validation")
                .build();
    }
}