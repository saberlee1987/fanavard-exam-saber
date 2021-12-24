package com.saber.fanavardexamsaber.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionResponseDto implements Serializable, Cloneable {
    public String identifier;
    private Double award;

    @Override
    public QuestionResponseDto clone() {
        try {
            return (QuestionResponseDto) super.clone();
        } catch (Exception ex) {
            return null;
        }
    }
}
