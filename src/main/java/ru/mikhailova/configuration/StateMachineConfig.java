package ru.mikhailova.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import ru.mikhailova.domain.statemachine.TaskEvent;
import ru.mikhailova.domain.statemachine.TaskState;
import ru.mikhailova.service.TaskService;
import ru.mikhailova.statemachine.action.AcceptedAction;
import ru.mikhailova.statemachine.action.ExecutedAction;
import ru.mikhailova.statemachine.action.ReworkedAction;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<TaskState, TaskEvent> {
    private final TaskService taskService;

    @Override
    public void configure(StateMachineStateConfigurer<TaskState, TaskEvent> states) throws Exception {
        states
                .withStates()
                .initial(TaskState.NEW)
                .state(TaskState.REWORKED)
                .state(TaskState.EXECUTED)
                .end(TaskState.ACCEPTED);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<TaskState, TaskEvent> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<TaskState, TaskEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(TaskState.NEW)
                .target(TaskState.EXECUTED)
                .event(TaskEvent.EXECUTE)
                .action(executedAction(taskService))

                .and()
                .withExternal()
                .source(TaskState.EXECUTED)
                .target(TaskState.REWORKED)
                .event(TaskEvent.TO_REWORK)
                .action(reworkedAction(taskService))

                .and()
                .withExternal()
                .source(TaskState.REWORKED)
                .target(TaskState.EXECUTED)
                .event(TaskEvent.REWORKED)
                .action(executedAction(taskService))

                .and()
                .withExternal()
                .source(TaskState.EXECUTED)
                .target(TaskState.ACCEPTED)
                .event(TaskEvent.ACCEPT)
                .action(acceptedAction(taskService));
    }

    @Bean
    public Action<TaskState, TaskEvent> reworkedAction(TaskService taskService) {
        return new ReworkedAction(taskService);
    }

    @Bean
    public Action<TaskState, TaskEvent> acceptedAction(TaskService taskService) {
        return new AcceptedAction(taskService);
    }

    @Bean
    public Action<TaskState, TaskEvent> executedAction(TaskService taskService) {
        return new ExecutedAction(taskService);
    }

    @Bean
    public StateMachinePersister<TaskState, TaskEvent, Long> persister() {
        return new DefaultStateMachinePersister<>(new TaskStateMachinePersister());
    }
}
