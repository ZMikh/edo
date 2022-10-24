package ru.mikhailova.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Автор поручения")
public class AuthorDto {
    @ApiModelProperty("Идентификатор автора поручения")
    private Long id;
    @ApiModelProperty("Фамилия автора поручения")
    private String lastName;
}
