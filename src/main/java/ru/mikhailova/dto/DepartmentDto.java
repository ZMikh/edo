package ru.mikhailova.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("Поразделение")
@Data
public class DepartmentDto {
    @ApiModelProperty("Идентификатор подразделения")
    private Long id;
    @ApiModelProperty("Наименование подразделения")
    private String name;
    @ApiModelProperty("Организация")
    private OrganizationDto organization;
}
