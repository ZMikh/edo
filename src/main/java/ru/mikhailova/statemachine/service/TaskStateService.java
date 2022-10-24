package ru.mikhailova.statemachine.service;

/**
 * Сервис по изменению статуса поручения
 */
public interface TaskStateService {
    /**
     * Выполнение поручения
     *
     * @param id id поручения
     * @return поручение выполнено/не выполнено
     */
    boolean executed(Long id);

    /**
     * Доработка поручения
     *
     * @param id id поручения
     * @return поручение доработано/не доработано
     */
    boolean reworked(Long id);

    /**
     * Приемка поручения
     *
     * @param id id поручения
     * @return поручение принято/не принято
     */
    boolean accepted(Long id);
}
