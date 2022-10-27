package ru.mikhailova.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Сотрудник
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(name = "Employee.withDepartment",
    attributeNodes = @NamedAttributeNode(value = "department", subgraph = "department.organization"),
    subgraphs = @NamedSubgraph(name = "department.organization", attributeNodes = @NamedAttributeNode("organization"))
)
public class Employee {
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
    private String job;
    /**
     * Подразделение организации
     */
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    /**
     * Список созданных сотрудником поручений
     */
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Task> createdTasks;
    /**
     * Список поручений на выполнении у сотрудника
     */
    @ManyToMany(mappedBy = "executors", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Task> assignedTasks;
}
