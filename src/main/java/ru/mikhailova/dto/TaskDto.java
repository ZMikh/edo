package ru.mikhailova.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel("Поручение")
public class TaskDto { // TODO подумать над именем
    @ApiModelProperty("Идентификатор поручения")
    private Long id;
    @ApiModelProperty("Предмет поручения")
    private String subject;
    @ApiModelProperty("Автор поручения")
    private AuthorDto author;
    @ApiModelProperty("Исполнители поручения")
    private List<ExecutorsDto> executors;
    @ApiModelProperty("Срок исполнения")
    private LocalDateTime terms;
    @ApiModelProperty("Признак контрольности")
    private Boolean isControlled;
    @ApiModelProperty("Признак исполнения")
    private Boolean isExecuted;
    @ApiModelProperty("Текст поручения")
    private String text;
    @ApiModelProperty("Состояние поручения")
    private String taskState;
}
