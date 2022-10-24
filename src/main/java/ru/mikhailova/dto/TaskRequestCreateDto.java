package ru.mikhailova.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Параметры для запроса по созданию поручения")
public class TaskRequestCreateDto {
    @ApiModelProperty("Предмет поручения")
    private String subject;
    @ApiModelProperty("Идентификатор автора поручения")
    private Long authorId;
    @ApiModelProperty("Идентификатор исполнителя поручения")
    private List<Long> executorsId;
    @ApiModelProperty("Текст поручения")
    private String text;
}
