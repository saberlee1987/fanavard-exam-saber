package com.saber.fanavardexamsaber.services;

import com.saber.fanavardexamsaber.dto.request.QuestionRequestDto;
import com.saber.fanavardexamsaber.dto.response.QuestionTwoResponse;

public interface SortApi {
    QuestionTwoResponse sortApi(QuestionRequestDto requestDto);
}
