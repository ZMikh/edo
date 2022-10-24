package ru.mikhailova.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Параметры для запроса по редактированию поручения")
public class TaskRequestUpdateDto {
    @ApiModelProperty("Срок исполнения")
    private LocalDateTime terms;
    @ApiModelProperty("Признак контрольности")
    private Boolean isControlled;
    @ApiModelProperty("Признак исполнения")
    private Boolean isExecuted;
    @ApiModelProperty("Текст поручения")
    private String text;
}
