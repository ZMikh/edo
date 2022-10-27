package ru.mikhailova.domain;

import lombok.*;
import ru.mikhailova.domain.statemachine.TaskState;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Поручение
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(name = "Task.withEmployees", attributeNodes = {
        @NamedAttributeNode(value = "author"),
        @NamedAttributeNode(value = "executors")})
public class Task {
    /**
     * Идентификатор поручения
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id")
    @SequenceGenerator(name = "task_id", sequenceName = "task_sequence", allocationSize = 1)
    private Long id;
    /**
     * Предмет поручения
     */
    private String subject;
    /**
     * Автор поручения
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_author_id")
    private Employee author;
    /**
     * Исполнители поручения
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_executors_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> executors;
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
    @Enumerated(EnumType.STRING)
    private TaskState taskState;
}
