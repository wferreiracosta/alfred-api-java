package br.com.wferreiracosta.alfred.config;

import br.com.wferreiracosta.alfred.config.props.PomProp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final PomProp pomProp;

    public SwaggerConfig(PomProp pomProp) {
        this.pomProp = pomProp;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.wferreiracosta.alfred.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.apiInfo());
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title(this.pomProp.projectName)
            .description(this.pomProp.getProjectDescription())
            .version(this.pomProp.getProjectVersion())
            .build();
    }

}
