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
     * Отправка поручения на доработку
     */
    TO_REWORK,
    /**
     * Доработанное поручение
     */
    REWORKED,
    /**
     * Приемка поручения
     */
    ACCEPT
}
