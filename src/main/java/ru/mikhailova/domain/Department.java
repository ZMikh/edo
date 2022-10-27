package ru.mikhailova.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Подразделение
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    /**
     * Идентификатор подразделения
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_id")
    @SequenceGenerator(name = "department_id", sequenceName = "department_sequence", allocationSize = 1)
    private Long id;
    /**
     * Наименование подразделения
     */
    private String name;
    /**
     * Контактные данные
     */
    private String contactInfo;
    /**
     * Руководитель подразделения
     */
    private String manager;
    /**
     * Список сотрудников подразделения
     */
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employee> employees;
    /**
     * Наименование организации
     */
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;
}
