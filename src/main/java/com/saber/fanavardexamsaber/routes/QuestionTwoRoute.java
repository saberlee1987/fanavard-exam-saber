package com.saber.fanavardexamsaber.routes;

import com.saber.fanavardexamsaber.dto.request.QuestionRequestDto;
import com.saber.fanavardexamsaber.dto.response.QuestionResponse;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class QuestionTwoRoute extends AbstractRestRouteBuilder {

    @Value(value = "${service.max-pool-size}")
    private Integer maxPoolSize;
    @Value(value = "${service.min-pool-size}")
    private Integer minPoolSize;

    @Override
    public void configure() throws Exception {
        super.configure();

        rest("/fanavard")
                .post("/question2")
                .id(Routes.FNAVARD_QUESTION_TWO_ROUTE)
                .description("FNAVARD_QUESTION_TWO_ROUTE")
                .responseMessage().code(200).responseModel(QuestionResponse.class).endResponseMessage()
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .enableCORS(true)
                .bindingMode(RestBindingMode.json)
                .type(QuestionRequestDto.class)
                .route()
                .routeId(Routes.FNAVARD_QUESTION_TWO_ROUTE)
                .routeGroup(Routes.FNAVARD_QUESTION_TWO_ROUTE_GROUP)
                .to(String.format("direct:%s", Routes.FNAVARD_QUESTION_TWO_ROUTE));

        from(String.format("direct:%s", Routes.FNAVARD_QUESTION_TWO_ROUTE))
                .routeId(Routes.FNAVARD_QUESTION_TWO_ROUTE_GATEWAY)
                .routeGroup(Routes.FNAVARD_QUESTION_TWO_ROUTE_GROUP)
                .threads().threadName(Routes.FNAVARD_QUESTION_TWO_ROUTE)
                .maxPoolSize(maxPoolSize)
                .poolSize(minPoolSize)
                .log("Request for question two ===> ${in.body}")
                .to("bean:sort-api?method=sortApi")
                .marshal().json(JsonLibrary.Jackson)
                .log("Response for question two ===> ${in.body}")
                .convertBodyTo(String.class)
                .unmarshal().json(JsonLibrary.Jackson)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));


    }
}
