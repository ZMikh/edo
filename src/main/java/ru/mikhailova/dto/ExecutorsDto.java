package ru.mikhailova.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Исполнители поручения")
public class ExecutorsDto {
    @ApiModelProperty("Идентификатор исполнителя")
    private Long id;
    @ApiModelProperty("Фамилия исполнителя")
    private String lastName;
}
