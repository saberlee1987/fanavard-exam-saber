package com.saber.fanavardexamsaber.routes;

import com.saber.fanavardexamsaber.dto.request.QuestionRequestDto;
import com.saber.fanavardexamsaber.dto.response.BitCointData;
import com.saber.fanavardexamsaber.dto.response.QuestionResponse;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class QuestionThreeRoute extends AbstractRestRouteBuilder {

    @Value(value = "${service.max-pool-size}")
    private Integer maxPoolSize;
    @Value(value = "${service.min-pool-size}")
    private Integer minPoolSize;

    @Value(value = "${service.question-three.url}")
    private String url;

    @Override
    public void configure() throws Exception {
        super.configure();

        rest("/fanavard")
                .post("/question3")
                .id(Routes.FNAVARD_QUESTION_THREE_ROUTE)
                .description("FNAVARD_QUESTION_THREE_ROUTE")
                .responseMessage().code(200).responseModel(QuestionResponse.class).endResponseMessage()
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .enableCORS(true)
                .bindingMode(RestBindingMode.json)
                .type(QuestionRequestDto.class)
                .route()
                .routeId(Routes.FNAVARD_QUESTION_THREE_ROUTE)
                .routeGroup(Routes.FNAVARD_QUESTION_THREE_ROUTE_GROUP)
                .to(String.format("direct:%s", Routes.FNAVARD_QUESTION_THREE_ROUTE));

        from(String.format("direct:%s", Routes.FNAVARD_QUESTION_THREE_ROUTE))
                .routeId(Routes.FNAVARD_QUESTION_THREE_ROUTE_GATEWAY)
                .routeGroup(Routes.FNAVARD_QUESTION_THREE_ROUTE_GROUP)
                .threads().threadName(Routes.FNAVARD_QUESTION_THREE_ROUTE)
                .maxPoolSize(maxPoolSize)
                .poolSize(minPoolSize)
                .log("Request for questionThree ===> ${in.body}")
                .to("bean:sort-api?method=questionThree")
                .marshal().json(JsonLibrary.Jackson)
                .log("Response for questionThree ===> ${in.body}")
                .convertBodyTo(String.class)
                .unmarshal().json(JsonLibrary.Jackson)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));



        from(String.format("direct:%s", Routes.CALL_QUESTION_THREE_ROUTE))
                .process(exchange -> {
                    LocalDate fromDate = LocalDate.of(2021, 9, 1);
                    LocalDate toDate = LocalDate.of(2021, 9, 30);
                    long from = fromDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
                    long to = toDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
                    int resolution = 60;
                    exchange.getIn().setHeader("symbol", "BTCIRT");
                    exchange.getIn().setHeader("resolution", resolution);
                    exchange.getIn().setHeader("from", from);
                    exchange.getIn().setHeader("to", to);
                })
                .toD(url + "?symbol=${in.header.symbol}&resolution=${in.header.resolution}&from=${in.header.from}&to=${in.header.to}")
                .convertBodyTo(String.class)
                .unmarshal().json(JsonLibrary.Jackson, BitCointData.class)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));


    }
}
