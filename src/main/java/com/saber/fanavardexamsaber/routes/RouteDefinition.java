package com.saber.fanavardexamsaber.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RouteDefinition extends RouteBuilder {
    @Value(value = "${service.api.base-path}")
    private String basePath;

    @Value(value = "${service.log.pretty-print}")
    private  String prettyPrint;

    @Value(value = "${service.api.swagger-path}")
    private String swaggerPath;

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .contextPath(basePath)
                .apiContextPath(swaggerPath)
                .apiProperty("api.title", "fanavard exam saber")
                .apiProperty("api.version", "version1.0-1400/10/03")
                .apiProperty("api.cors", "true")
                .bindingMode(RestBindingMode.json)
                .enableCORS(true)
                .dataFormatProperty("prettyPrint", prettyPrint);
    }
}
