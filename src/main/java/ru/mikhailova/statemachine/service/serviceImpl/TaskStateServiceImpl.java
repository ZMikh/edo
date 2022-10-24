package ru.mikhailova.statemachine.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import ru.mikhailova.domain.statemachine.TaskEvent;
import ru.mikhailova.domain.statemachine.TaskState;
import ru.mikhailova.statemachine.service.TaskStateService;

@Service
@RequiredArgsConstructor
public class TaskStateServiceImpl implements TaskStateService {

    private final StateMachineFactory<TaskState, TaskEvent> stateMachineFactory;
    private final StateMachinePersister<TaskState, TaskEvent, Long> persister;

    @Override
    public boolean executed(Long id) {
        StateMachine<TaskState, TaskEvent> stateMachine;
        try {
            stateMachine = stateMachineFactory.getStateMachine();
            persister.restore(stateMachine, id);
            return stateMachine.sendEvent(TaskEvent.EXECUTE);
        } catch (Exception e) {
            stateMachine = stateMachineFactory.getStateMachine();
            return persistState(id, stateMachine);
        }
    }

    @Override
    public boolean reworked(Long id) {
        StateMachine<TaskState, TaskEvent> stateMachine = stateMachineFactory.getStateMachine();
        try {
            persister.restore(stateMachine, id);
            return stateMachine.sendEvent(TaskEvent.REWORK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean accepted(Long id) {
        StateMachine<TaskState, TaskEvent> stateMachine = stateMachineFactory.getStateMachine();
        try {
            persister.restore(stateMachine, id);
            return stateMachine.sendEvent(TaskEvent.ACCEPT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean persistState(Long id, StateMachine<TaskState, TaskEvent> stateMachine) {
        stateMachine.getExtendedState().getVariables().put("TASK_ID", id);
        try {
            stateMachine.sendEvent(TaskEvent.EXECUTE);
            persister.persist(stateMachine, id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
