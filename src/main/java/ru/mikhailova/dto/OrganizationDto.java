package ru.mikhailova.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("Организация")
@Data
public class OrganizationDto {
    @ApiModelProperty("Идентификатор организации")
    private Long id;
    @ApiModelProperty("Наименование организации")
    private String name;
}
