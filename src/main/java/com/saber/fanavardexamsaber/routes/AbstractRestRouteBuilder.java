package com.saber.fanavardexamsaber.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractRestRouteBuilder extends RouteBuilder {
    @Autowired
    private ObjectMapper mapper;
    @Override
    public void configure() throws Exception {

    }
}
