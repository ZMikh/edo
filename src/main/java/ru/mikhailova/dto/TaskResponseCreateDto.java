package ru.mikhailova.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel("Параметры для ответа по созданию поручения")
public class TaskResponseCreateDto {
    @ApiModelProperty("Идентификатор поручения")
    private Long id;
    @ApiModelProperty("Предмет поручения")
    private String subject;
    @ApiModelProperty("Идентификатор и фамилия автора поручения")
    private AuthorDto author;
    @ApiModelProperty("Идентификаторы и фамилии исполнителей поручения")
    private List<ExecutorsDto> executors;
    @ApiModelProperty("Текст поручения")
    private String text;
    @ApiModelProperty("Срок исполнения")
    private LocalDateTime terms;
}
