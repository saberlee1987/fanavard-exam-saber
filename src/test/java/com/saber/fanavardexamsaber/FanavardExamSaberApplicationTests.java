package com.saber.fanavardexamsaber;

import com.saber.fanavardexamsaber.dto.HelloDto;
import com.saber.fanavardexamsaber.routes.Headers;
import com.saber.fanavardexamsaber.routes.Routes;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
class FanavardExamSaberApplicationTests {

    @Autowired
    private ProducerTemplate producerTemplate;
    @Test
    void hello() {
        Exchange responseExchange = producerTemplate.send(String.format("direct:%s", Routes.SAY_HELLO_ROUTE), exchange -> {
            exchange.getIn().setHeader(Headers.FIRST_NAME, "Bruce");
            exchange.getIn().setHeader(Headers.LAST_NAME, "Lee");
        });
        Integer statusCode = responseExchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
        HelloDto body = responseExchange.getIn().getBody(HelloDto.class);
        Assertions.assertEquals(statusCode, HttpStatus.OK.value());
        Assertions.assertNotNull(body);
    }

}
