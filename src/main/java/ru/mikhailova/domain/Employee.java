package ru.mikhailova.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;
//TODO eager fetchtype remove
/**
 * Сотрудники
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee { // TODO Java Doc
    /**
     * Идентификатор сотрудника
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id")
    @SequenceGenerator(name = "employee_id", sequenceName = "employee_sequence", allocationSize = 1)
    private Long id;
    /**
     * Фамилия сотрудника
     */
    private String lastName;
    /**
     * Имя сотрудника
     */
    private String firstName;
    /**
     * Отчество сотрудника
     */
    private String patronymicName;
    /**
     * Должность сотрудника
     */
    private String job; // TODO сделать просто String и удалить Job
    /**
     * Подразделение организации
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "department_id")
    private Department department;
    /**
     * Список созданных сотрудником поручений
     */
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<Task> createdTasks;
    /**
     * Список поручений на выполнении у сотрудника
     */
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "executors")
    private List<Task> assignedTasks;
}
