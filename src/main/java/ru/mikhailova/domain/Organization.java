package ru.mikhailova.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Организация
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Organization {
    /**
     * Идентификатор организации
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organization_id")
    @SequenceGenerator(name = "organization_id", sequenceName = "organization_sequence", allocationSize = 1)
    private Long id;
    /**
     * Наименование организации
     */
    private String name;
    /**
     * Физический адрес организации
     */
    private String actualAddress;
    /**
     * Юридический адрес организации
     */
    private String legalAddress;
    /**
     * Руководитель организации
     */
    private String ceo;
    /**
     * Список подразделений организации
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organization")
    private List<Department> departments;
}
