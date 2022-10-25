package ru.mikhailova.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import ru.mikhailova.dto.TaskRequestCreateDto;
import ru.mikhailova.dto.TaskRequestUpdateDto;
import ru.mikhailova.repository.EmployeeRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public abstract class AbstractIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected EmployeeRepository employeeRepository;

    protected <T> T performPostApp(TaskRequestCreateDto taskRequestCreateDto, String url,
                                   Class<T> responseClassDto) throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/api/v1/task" + url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequestCreateDto)))
                .andDo(print())
                .andExpect(status().isOk());

        return objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), responseClassDto);
    }

    protected <T> T performGetByIdApp(String url, Class<T> responseClassDto) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/task" + url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), responseClassDto);
    }

    protected <T> T performGetByQueryApp(String url, TypeReference<T> responseClassDto,
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

    protected <T> T performPutApp(TaskRequestUpdateDto taskRequestUpdateDto,
                                  String url, Class<T> responseClassDto) throws Exception {
        ResultActions resultActions = mockMvc.perform(put("/api/v1/task" + url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequestUpdateDto)))
                .andDo(print())
                .andExpect(status().isOk());

        return objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), responseClassDto);
    }

    protected void performDeleteApp(String url) throws Exception {
        mockMvc.perform(delete("/api/v1/task" + url))
                .andDo(print())
                .andExpect(status().isOk());
    }

    protected ResultActions performExceptionPutApp(TaskRequestUpdateDto taskRequestUpdateDto,
                                                   String url) throws Exception {
        return mockMvc.perform(put("/api/v1/task" + url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequestUpdateDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
