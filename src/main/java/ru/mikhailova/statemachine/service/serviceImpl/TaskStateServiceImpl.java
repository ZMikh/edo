package ru.mikhailova.statemachine.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import ru.mikhailova.domain.statemachine.TaskEvent;
import ru.mikhailova.domain.statemachine.TaskState;
import ru.mikhailova.repository.TaskRepository;
import ru.mikhailova.statemachine.service.TaskStateService;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskStateServiceImpl implements TaskStateService {
    private final StateMachineFactory<TaskState, TaskEvent> stateMachineFactory;
    private final StateMachinePersister<TaskState, TaskEvent, Long> persister;
    private final TaskRepository taskRepository;

    @Override
    public boolean executed(Long id) {
        StateMachine<TaskState, TaskEvent> stateMachine;
        try {
            stateMachine = stateMachineFactory.getStateMachine();
            persister.restore(stateMachine, id);
            boolean result = stateMachine.sendEvent(TaskEvent.REWORKED);
            persister.persist(stateMachine, id);
            return result;
        } catch (Exception e) {
            if(taskRepository.findById(id).isEmpty()) {
                return false;
            }
            stateMachine = stateMachineFactory.getStateMachine();
            return createAndPersistState(id, stateMachine);
        }
    }

    @Override
    public boolean reworked(Long id) {
        StateMachine<TaskState, TaskEvent> stateMachine = stateMachineFactory.getStateMachine();
        try {
            persister.restore(stateMachine, id);
            boolean result = stateMachine.sendEvent(TaskEvent.TO_REWORK);
            persister.persist(stateMachine, id);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean accepted(Long id) {
        StateMachine<TaskState, TaskEvent> stateMachine = stateMachineFactory.getStateMachine();
        try {
            persister.restore(stateMachine, id);
            boolean result = stateMachine.sendEvent(TaskEvent.ACCEPT);
            persister.persist(stateMachine, id);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    private boolean createAndPersistState(Long id, StateMachine<TaskState, TaskEvent> stateMachine) {
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
