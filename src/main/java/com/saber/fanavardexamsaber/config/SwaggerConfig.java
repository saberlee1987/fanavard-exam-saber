package com.saber.fanavardexamsaber.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;
import java.util.List;
@Configuration
@Primary
@EnableSwagger2
public class SwaggerConfig implements SwaggerResourcesProvider {

    @Value(value = "${service.api.base-path}")
    private String apiBasePath;
    @Value(value = "${service.api.swagger-path}")
    private String swaggerPath;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> swaggerResources = new ArrayList<>();
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setUrl(String.format("%s%s",apiBasePath,swaggerPath));
        swaggerResource.setName("fanavard-exam-saber");
        swaggerResources.add(swaggerResource);
        return swaggerResources;
    }
}
