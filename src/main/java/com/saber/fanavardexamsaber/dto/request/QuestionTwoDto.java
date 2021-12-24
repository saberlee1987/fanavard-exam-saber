package com.saber.fanavardexamsaber.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuestionTwoDto implements Serializable {
    public String identifier;
    public List<Double> array_agg;
}
