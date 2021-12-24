package com.saber.fanavardexamsaber.services.impl;

import com.saber.fanavardexamsaber.dto.request.QuestionRequestDto;
import com.saber.fanavardexamsaber.dto.request.QuestionTwoDto;
import com.saber.fanavardexamsaber.dto.response.BitCointData;
import com.saber.fanavardexamsaber.dto.response.QuestionResponse;
import com.saber.fanavardexamsaber.dto.response.QuestionResponseDto;
import com.saber.fanavardexamsaber.routes.Routes;
import com.saber.fanavardexamsaber.services.SortApi;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Service(value = "sort-api")
public class SortApiImpl implements SortApi {

    @Value(value = "${service.question-two.rate}")
    private Integer rate;

    @Autowired
    private ProducerTemplate producerTemplate;

    @Override
    public QuestionResponse sortApi(QuestionRequestDto requestDto) {
        QuestionResponse response = new QuestionResponse();

        List<QuestionResponseDto> questionResponseDtoList = new ArrayList<>();
        QuestionResponseDto responseDto = new QuestionResponseDto();

        for (QuestionTwoDto data : requestDto.getData()) {
            QuestionResponseDto questionResponseDtoClone = responseDto.clone();
            questionResponseDtoClone.setIdentifier(data.identifier);
            questionResponseDtoClone.setAward(rate * score(data.getArray_agg()));
            questionResponseDtoList.add(questionResponseDtoClone);
        }
        response.setResponse(questionResponseDtoList);
        return response;
    }

    @Override
    public QuestionResponse questionThree(QuestionRequestDto requestDto) {
        QuestionResponse response = sortApi(requestDto);

        Exchange responseExchange = producerTemplate.send(String.format("direct:%s", Routes.CALL_QUESTION_THREE_ROUTE), exchange -> {
        });
        BitCointData body = responseExchange.getIn().getBody(BitCointData.class);
        OptionalDouble max = body.h.stream().mapToDouble(v -> v).max();
        double highPrice;
        if (max.isPresent()) {
            highPrice = max.getAsDouble();
        } else {
            highPrice = 1.0;
        }
        for (QuestionResponseDto responseDto : response.getResponse()) {
            responseDto.setAward(responseDto.getAward() * highPrice);
        }
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
