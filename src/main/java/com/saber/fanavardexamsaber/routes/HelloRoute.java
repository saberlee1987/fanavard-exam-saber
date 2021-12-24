package com.saber.fanavardexamsaber.routes;

import com.saber.fanavardexamsaber.dto.response.HelloDto;
import org.apache.camel.Exchange;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class HelloRoute extends AbstractRestRouteBuilder {

    @Value(value = "${service.max-pool-size}")
    private Integer maxPoolSize;
    @Value(value = "${service.min-pool-size}")
    private Integer minPoolSize;

    @Override
    public void configure() throws Exception {
        super.configure();

        rest("/hello")
                .get("/say-hello")
                .id(Routes.SAY_HELLO_ROUTE)
                .description("say hello")
                .responseMessage().code(200).responseModel(HelloDto.class).endResponseMessage()
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .enableCORS(true)
                .param().name("firstName").type(RestParamType.query).dataType("string").example("saber").required(true).endParam()
                .param().name("lastName").type(RestParamType.query).dataType("string").example("azizi").required(true).endParam()
                .route()
                .routeId(Routes.SAY_HELLO_ROUTE)
                .routeGroup(Routes.SAY_HELLO_ROUTE_GROUP)
                .to(String.format("direct:%s", Routes.SAY_HELLO_ROUTE));

        from(String.format("direct:%s", Routes.SAY_HELLO_ROUTE))
                .threads().threadName(Routes.SAY_HELLO_ROUTE)
                .maxPoolSize(maxPoolSize)
                .poolSize(minPoolSize)
                .log("Request for sayHello ===> firstName ${in.header.firstName} , lastName : ${in.header.lastName}")
                .process(exchange -> {
                    String firstName = exchange.getIn().getHeader("firstName", String.class);
                    String lastName = exchange.getIn().getHeader("lastName", String.class);
                    HelloDto helloDto = new HelloDto();
                    helloDto.setMessage(String.format("Hello %s %s", firstName, lastName));
                    exchange.getIn().setBody(helloDto);
                })
                .log("Response for sayHello ===> ${in.body}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));


    }
}
