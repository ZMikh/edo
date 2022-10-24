package ru.mikhailova.configuration;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import ru.mikhailova.domain.statemachine.TaskEvent;
import ru.mikhailova.domain.statemachine.TaskState;

import java.util.HashMap;

public class TaskStateMachinePersister implements StateMachinePersist<TaskState, TaskEvent, Long> {
    private final HashMap<Long, StateMachineContext<TaskState, TaskEvent>> context = new HashMap<>();

    @Override
    public void write(StateMachineContext<TaskState, TaskEvent> stateMachineContext, Long id) throws Exception {
        context.put(id, stateMachineContext);
    }

    @Override
    public StateMachineContext<TaskState, TaskEvent> read(Long id) throws Exception {
        StateMachineContext<TaskState, TaskEvent> taskStateTaskEventStateMachineContext = context.get(id);
        if (taskStateTaskEventStateMachineContext == null) {
            throw new RuntimeException("Not found state with id: " + id);
        }
        return taskStateTaskEventStateMachineContext;
    }
}
