package ru.mikhailova.statemachine.action;

import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import ru.mikhailova.domain.statemachine.TaskEvent;
import ru.mikhailova.domain.statemachine.TaskState;
import ru.mikhailova.service.TaskService;
import ru.mikhailova.service.UpdateParam;

import static ru.mikhailova.domain.statemachine.TaskState.ACCEPTED;

@RequiredArgsConstructor
public class AcceptedAction implements Action<TaskState, TaskEvent> {
    private final TaskService taskService;

    @Override
    public void execute(StateContext<TaskState, TaskEvent> stateContext) {
        Long id = stateContext.getExtendedState().get("TASK_ID", Long.class);
        UpdateParam updateParam = new UpdateParam();
        updateParam.setIsControlled(true);
        updateParam.setTaskState(ACCEPTED);
        taskService.updateTask(id, updateParam);
    }
}
