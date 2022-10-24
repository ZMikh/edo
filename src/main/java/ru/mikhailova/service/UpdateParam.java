package ru.mikhailova.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mikhailova.domain.statemachine.TaskState;

import java.time.LocalDateTime;

/**
 * Параметры для обновления поручения
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateParam { // TODO java doc
    /**
     * Сроки исполнения поручения
     */
    private LocalDateTime terms;
    /**
     * Признак контрольности поручения
     */
    private Boolean isControlled;
    /**
     * Признак исполнения поручения
     */
    private Boolean isExecuted;
    /**
     * Текст поручения
     */
    private String text;
    /**
     * Статус поручения
     */
    private TaskState taskState;
}
