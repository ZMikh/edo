package ru.mikhailova.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Параметры для запроса по созданию поручения")
public class TaskRequestCreateDto {
    @NotBlank
    @ApiModelProperty("Предмет поручения")
    private String subject;
    @NotNull
    @ApiModelProperty("Идентификатор автора поручения")
    private Long authorId;
    @NotEmpty
    @ApiModelProperty("Идентификатор исполнителя поручения")
    private List<Long> executorsId;
    @ApiModelProperty("Текст поручения")
    private String text;
    @ApiModelProperty("Срок исполнения")
    private LocalDateTime terms;
}
