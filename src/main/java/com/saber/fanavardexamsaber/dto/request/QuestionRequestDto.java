package com.saber.fanavardexamsaber.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class QuestionRequestDto implements Serializable {
    private List<QuestionTwoDto> data;
}
