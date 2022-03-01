package br.com.wferreiracosta.alfred.config;

import br.com.wferreiracosta.alfred.config.security.JWTAuthenticationFilter;
import br.com.wferreiracosta.alfred.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Environment environment;

    @Autowired
    private JwtUtil jwtUtil;

    private static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**",
            "/swagger-ui.html"
    };

    private static final String[] PUBLIC_MATCHERS_POST = {
            "/clientes/**"
    };

    private static final String[] PUBLIC_MATCHERS_GET = {
            "/produtos/**",
            "/categorias/**"
    };

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
                "/swagger-ui.html", "/webjars/**");
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        verifyProfile(httpSecurity);

        httpSecurity.cors()
                .and().csrf().disable();

        if(!Arrays.asList(environment.getActiveProfiles()).contains("test")){
            httpSecurity.authorizeRequests()
                    .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
                    .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
                    .antMatchers(PUBLIC_MATCHERS).permitAll()
                    .anyRequest().authenticated();

            httpSecurity.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
        }

        httpSecurity.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private void verifyProfile(HttpSecurity httpSecurity) throws Exception {
        if(Arrays.asList(environment.getActiveProfiles()).contains("dev")){
            httpSecurity.headers().frameOptions().disable();
        }
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

}
