package ru.mikhailova.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("Сотрудник")
@Data
public class EmployeeDto {
    @ApiModelProperty("Идентификатор сотрудника")
    private Long id;
    @ApiModelProperty("Имя сотрудника")
    private String firstName;
    @ApiModelProperty("Фамилия сотрудника")
    private String lastName;
    @ApiModelProperty("Должность сотрудника")
    private String job;
    @ApiModelProperty("Подразделение сотрудника")
    private DepartmentDto department;
}
