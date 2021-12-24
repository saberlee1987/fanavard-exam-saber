package com.saber.fanavardexamsaber.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionTwoResponseDto implements Serializable, Cloneable {
    public String identifier;
    private Double award;

    @Override
    public QuestionTwoResponseDto clone() {
        try {
            return (QuestionTwoResponseDto) super.clone();
        } catch (Exception ex) {
            return null;
        }
    }
}
