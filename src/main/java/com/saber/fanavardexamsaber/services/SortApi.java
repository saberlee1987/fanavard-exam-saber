package com.saber.fanavardexamsaber.services;

import com.saber.fanavardexamsaber.dto.request.QuestionRequestDto;
import com.saber.fanavardexamsaber.dto.response.QuestionResponse;

public interface SortApi {
    QuestionResponse sortApi(QuestionRequestDto requestDto);
    QuestionResponse questionThree(QuestionRequestDto requestDto);
}
