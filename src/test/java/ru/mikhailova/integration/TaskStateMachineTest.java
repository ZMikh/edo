package ru.mikhailova.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;
import ru.mikhailova.domain.Task;
import ru.mikhailova.domain.statemachine.TaskEvent;
import ru.mikhailova.domain.statemachine.TaskState;
import ru.mikhailova.repository.TaskRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


public class TaskStateMachineTest extends AbstractIntegrationTest {
    @Autowired
    private StateMachineFactory<TaskState, TaskEvent> stateMachineFactory;
    private StateMachine<TaskState, TaskEvent> stateMachine;
    @MockBean
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.getExtendedState().getVariables().put("TASK_ID", 1L);
        doReturn(Optional.of(new Task())).when(taskRepository).findById(any());
    }

    @Test
    void machineInitializationCheck() {
        assertThat(stateMachine.getState().getId()).isEqualTo(TaskState.NEW);
    }

    @Test
    void machineWorkFlowWithPlanCheck() throws Exception {
        StateMachineTestPlanBuilder.<TaskState, TaskEvent>builder()
                .defaultAwaitTime(2)
                .stateMachine(stateMachine)
                .step()
                    .expectState(TaskState.NEW)
                .and()
                .step()
                    .sendEvent(TaskEvent.EXECUTE)
                    .expectStateChanged(1)
                    .expectStates(TaskState.EXECUTED)
                    .expectVariable("TASK_ID")
                .and()
                .step()
                    .sendEvent(TaskEvent.TO_REWORK)
                    .expectStateChanged(1)
                    .expectState(TaskState.REWORKED)
                .and()
                .build()
                .test();
    }


}
