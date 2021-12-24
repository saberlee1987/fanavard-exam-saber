package com.saber.fanavardexamsaber.services.impl;

import com.saber.fanavardexamsaber.dto.request.QuestionRequestDto;
import com.saber.fanavardexamsaber.dto.request.QuestionTwoDto;
import com.saber.fanavardexamsaber.dto.response.QuestionTwoResponse;
import com.saber.fanavardexamsaber.dto.response.QuestionTwoResponseDto;
import com.saber.fanavardexamsaber.services.SortApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "sort-api")
public class SortApiImpl implements SortApi {

    @Value(value = "${service.question-two.rate}")
    private Integer rate;

    @Override
    public QuestionTwoResponse sortApi(QuestionRequestDto requestDto) {
        QuestionTwoResponse response = new QuestionTwoResponse();

        List<QuestionTwoResponseDto> questionTwoResponseDtoList = new ArrayList<>();
        QuestionTwoResponseDto responseDto = new QuestionTwoResponseDto();

        for (QuestionTwoDto data : requestDto.getData()) {
            QuestionTwoResponseDto questionTwoResponseDtoClone = responseDto.clone();
            questionTwoResponseDtoClone.setIdentifier(data.identifier);
            questionTwoResponseDtoClone.setAward(rate * score(data.getArray_agg()));
            questionTwoResponseDtoList.add(questionTwoResponseDtoClone);
        }
        response.setResponse(questionTwoResponseDtoList);
        return response;
    }

    private Double score(List<Double> scores) {
        double score = 0.0;
        for (Double a : scores) {
            score += a * getSi(a);
        }
        return score;
    }

    private int getSi(double score) {
        if (score >= 75)
            return 500;
        else if (score < 75 && score >= 50)
            return 400;
        else if (score < 50 && score >= 25)
            return 300;
        else return 100;
    }
}
