package ru.mikhailova.domain.statemachine;

/**
 * События, инициирующие переходы между статусами, для TaskStateMachine
 */
public enum TaskEvent {
    /**
     * Выполнение поручения
     */
    EXECUTE,
    /**
     * Доработка поручения
     */
    REWORK,
    /**
     * Приемка поручения
     */
    ACCEPT
}
