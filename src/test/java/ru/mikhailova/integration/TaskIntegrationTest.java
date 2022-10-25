package ru.mikhailova.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import ru.mikhailova.domain.Employee;
import ru.mikhailova.domain.Task;
import ru.mikhailova.dto.TaskDto;
import ru.mikhailova.dto.TaskRequestCreateDto;
import ru.mikhailova.dto.TaskRequestUpdateDto;
import ru.mikhailova.dto.TaskResponseCreateDto;
import ru.mikhailova.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class TaskIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TaskRepository taskRepository;


    private final TypeReference<List<TaskDto>> listTaskDtoTypeReference = new TypeReference<>() {
    };

    private Task task;

    @BeforeEach
    void setUp() {
        task = addTask();
        taskRepository.save(task);

        Employee employee = addEmployee();
        employeeRepository.save(employee);
    }

    @AfterEach
    void tearDown() {
        taskRepository.deleteAll();
    }

    @Test
    void couldCreateTask() throws Exception {
        TaskRequestCreateDto taskRequestCreateDto = new TaskRequestCreateDto();
        taskRequestCreateDto.setSubject("Attorney for Lalo");
        taskRequestCreateDto.setAuthorId(1L);
        taskRequestCreateDto.setExecutorsId(List.of(1L));
        taskRequestCreateDto.setText("Manage the Defense");

        TaskResponseCreateDto taskResponseCreateDto =
                performPostApp(taskRequestCreateDto, "/create", TaskResponseCreateDto.class);

        Optional<Task> task = taskRepository.findById(taskResponseCreateDto.getId());

        assertThat(task.isEmpty()).isFalse();
    }

    @Test
    void couldUpdateTaskParams() throws Exception {

        TaskRequestUpdateDto taskRequestUpdateDto = new TaskRequestUpdateDto();
        taskRequestUpdateDto.setTerms(LocalDateTime.of(2022, 10, 28, 10, 0, 0));
        taskRequestUpdateDto.setIsControlled(Boolean.TRUE);
        taskRequestUpdateDto.setIsExecuted(Boolean.TRUE);
        taskRequestUpdateDto.setText("Consult");

        TaskDto dto = performPutApp(taskRequestUpdateDto, "/update/" + task.getId(), TaskDto.class);

        assertThat(dto.getIsExecuted()).isTrue();
        assertThat(dto.getIsControlled()).isTrue();
        assertThat(dto.getTerms()).isBetween(
                LocalDateTime.of(2022, 10, 1, 0, 0, 0),
                LocalDateTime.of(2022, 11, 1, 0, 0, 0)
        );

        assertThat(dto.getText()).containsIgnoringCase("consult");
    }


    @Test
    void couldGetTaskById() throws Exception {
        TaskDto dto = performGetByIdApp("/get-by-id/" + task.getId(), TaskDto.class);

        assertThat(dto.getIsControlled()).isFalse();
        assertThat(dto.getIsExecuted()).isFalse();
    }

    @Test
    void couldDeleteTask() throws Exception {
        performDeleteApp("/delete-by-id/" + task.getId());
        boolean existsById = taskRepository.existsById(task.getId());

        assertThat(existsById).isFalse();
    }

    @Test
    void couldGetTaskByQuery() throws Exception {
        Task task1 = addTask();
        task1.setIsExecuted(true);
        task1.setIsControlled(false);
        taskRepository.save(task1);

        Task task2 = addTask();
        task2.setIsExecuted(true);
        task2.setIsControlled(true);
        taskRepository.save(task2);

        Task task3 = addTask();
        task3.setIsExecuted(false);
        task3.setIsControlled(false);
        taskRepository.save(task3);

        List<TaskDto> dtos1 = performGetByQueryApp("/get-by-query", listTaskDtoTypeReference, 0,
                1, true, false);

        assertThat(dtos1.size()).isEqualTo(1);
        assertThat(dtos1.get(0).getId()).isEqualTo(task1.getId());

        List<TaskDto> dtos2 = performGetByQueryApp("/get-by-query", listTaskDtoTypeReference, 1,
                1, true, false);

        assertThat(dtos2.size()).isEqualTo(0);
    }

    @Test
    void couldHandleValidationException() throws Exception {
        ResultActions resultActions = performExceptionPutApp(new TaskRequestUpdateDto(), "/update/100");

        assertThat(resultActions.andReturn().getResponse().getContentAsString()).isEqualTo("NOTHING THERE");
    }
    // TODO Вытащить запросы в отдельные методы с аргумента
    Task addTask() {
        return Task.builder()
                .isControlled(false)
                .isExecuted(false)
                .text("Post a bond")
                .build();
    }

    Employee addEmployee() {
        return Employee.builder()
                .lastName("Goodman")
                .build();
    }

}
