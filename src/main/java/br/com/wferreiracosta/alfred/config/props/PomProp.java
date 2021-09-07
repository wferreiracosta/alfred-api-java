package br.com.wferreiracosta.alfred.config.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class PomProp {
    @Value("${pom.project.version}")
    public String projectVersion;
    @Value("${pom.project.description}")
    public String projectDescription;
    @Value("${pom.project.name}")
    public String projectName;
}
