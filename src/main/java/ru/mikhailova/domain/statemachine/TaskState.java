package ru.mikhailova.domain.statemachine;

/**
 * Статусы поручения для TaskStateMachine
 */
public enum TaskState {
    /**
     * Поручение создано
     */
    NEW,
    /**
     * Поручение выполнено
     */
    EXECUTED,
    /**
     * Поручение доработано
     */
    REWORKED,
    /**
     * Поручение принято
     */
    ACCEPTED
}
