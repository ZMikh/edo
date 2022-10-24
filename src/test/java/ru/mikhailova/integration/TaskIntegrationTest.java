package ru.mikhailova.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class TaskIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository repository;

    private final TypeReference<List<TaskDto>> listTaskDtoTypeReference = new TypeReference<>() {
    };

    @Test
    void couldRegisterNewTask() throws Exception {
        TaskRequestCreateDto taskRequestCreateDto = new TaskRequestCreateDto();
        taskRequestCreateDto.setSubject("Task1");
        taskRequestCreateDto.setAuthorId(1L);
        taskRequestCreateDto.setText("Application Design");

        TaskResponseCreateDto taskResponseCreateDto =
                performPostApp(taskRequestCreateDto, "/create", TaskResponseCreateDto.class);

        Optional<Task> task = repository.findById(taskResponseCreateDto.getId());
        assertThat(task.isEmpty()).isFalse();
    }

    @Test
    void couldUpdateTaskParams() throws Exception {
        Task entity = addTask();
        repository.save(entity);

        TaskRequestUpdateDto taskRequestUpdateDto = new TaskRequestUpdateDto();
        taskRequestUpdateDto.setTerms(LocalDateTime.of(2022, 10, 28, 10, 0, 0));
        taskRequestUpdateDto.setIsControlled(Boolean.TRUE);
        taskRequestUpdateDto.setIsExecuted(Boolean.TRUE);
        taskRequestUpdateDto.setText("Application Testing");

        TaskDto dto = performPutApp(taskRequestUpdateDto, "/update/" + entity.getId(), TaskDto.class);

        assertThat(dto.getIsExecuted()).isTrue();
        assertThat(dto.getIsControlled()).isTrue();
        assertThat(dto.getTerms()).isBetween(
                LocalDateTime.of(2022, 10, 1, 0, 0, 0),
                LocalDateTime.of(2022, 11, 1, 0, 0, 0)
        );
        assertThat(dto.getText()).containsIgnoringCase("test");
    }


    @Test
    void couldGetTaskById() throws Exception {
        Task entity = addTask();
        repository.save(entity);

        TaskDto dto = performGetByIdApp("/get-by-id/" + entity.getId(), TaskDto.class);

        assertThat(dto.getIsControlled()).isFalse();
        assertThat(dto.getIsExecuted()).isFalse();
    }

    @Test
    void couldDeleteTask() throws Exception {
        Task entity = addTask();
        repository.save(entity);

        mockMvc.perform(delete("/api/v1/task/delete-by-id/" + entity.getId()))
                .andDo(print())
                .andExpect(status().isOk());

        boolean existsById = repository.existsById(entity.getId());
        assertThat(existsById).isFalse();
    }

    @Test
    void couldGetTaskByQuery() throws Exception {
        Task task1 = addTask();
        task1.setIsExecuted(true);
        task1.setIsControlled(false);
        repository.save(task1);

        Task task2 = addTask();
        task2.setIsExecuted(true);
        task2.setIsControlled(true);
        repository.save(task2);

        Task task3 = addTask();
        task3.setIsExecuted(false);
        task3.setIsControlled(false);
        repository.save(task3);

        List<TaskDto> dtos1 = performGetByQueryApp(
                "/get-by-query", listTaskDtoTypeReference, 0, 1, true, false
        );
        assertThat(dtos1.size()).isEqualTo(1);
        assertThat(dtos1.get(0).getId()).isEqualTo(task1.getId());

        List<TaskDto> dtos2 = performGetByQueryApp(
                "/get-by-query", listTaskDtoTypeReference, 1, 1, true, false
        );
        assertThat(dtos2.size()).isEqualTo(0);
    }

    @Test
    void couldHandleValidationException() throws Exception {
        Task task = addTask();
        repository.save(task);

        ResultActions resultActions = performExceptionPutApp(new TaskRequestUpdateDto(), "/update/100");
        assertThat(resultActions.andReturn().getResponse().getContentAsString()).isEqualTo("NOTHING THERE");
    }

    // TODO Вытащить запросы в отдельные методы с аргумента
    private <T> T performPostApp(TaskRequestCreateDto taskRequestCreateDto, String url,
                                 Class<T> responseClassDto) throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/api/v1/task" + url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequestCreateDto)))
                .andDo(print())
                .andExpect(status().isOk());

        return objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), responseClassDto);
    }

    private <T> T performGetByIdApp(String url, Class<T> responseClassDto) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/task" + url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), responseClassDto);
    }

    private <T> T performGetByQueryApp(String url, TypeReference<T> responseClassDto,
                                       int pageNumber, int pageSize,
                                       boolean isExecuted, boolean isControlled) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/task" + url)
                        .param("pageNumber", String.valueOf(pageNumber))
                        .param("pageSize", String.valueOf(pageSize))
                        .param("isExecuted", String.valueOf(isExecuted))
                        .param("isControlled", String.valueOf(isControlled))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), responseClassDto);
    }

    private <T> T performPutApp(TaskRequestUpdateDto taskRequestUpdateDto,
                                String url, Class<T> responseClassDto) throws Exception {
        ResultActions resultActions = mockMvc.perform(put("/api/v1/task" + url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequestUpdateDto)))
                .andDo(print())
                .andExpect(status().isOk());

        return objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), responseClassDto);
    }

    public ResultActions performExceptionPutApp(TaskRequestUpdateDto taskRequestUpdateDto,
                                                String url) throws Exception {
        ResultActions resultActions = mockMvc.perform(put("/api/v1/task" + url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequestUpdateDto)))
                .andDo(print())
                .andExpect(status().isNotFound());

        return resultActions;
    }

    Task addTask() {
        return Task.builder()
                .subject("Task1")
                .author(null)
                .executors(null)
                .isControlled(false)
                .isExecuted(false)
                .text("Application Enhancement")
                .build();
    }
}
