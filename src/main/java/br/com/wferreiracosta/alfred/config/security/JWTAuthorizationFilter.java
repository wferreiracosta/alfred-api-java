package br.com.wferreiracosta.alfred.config.security;

import br.com.wferreiracosta.alfred.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static java.util.Objects.isNull;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                                  UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  AuthenticationEntryPoint authenticationEntryPoint, JwtUtil jwtUtil,
                                  UserDetailsService userDetailsService) {
        super(authenticationManager, authenticationEntryPoint);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        final var header = request.getHeader("Authorization");
        if(!isNull(header) && header.startsWith("Bearer ")){
            final var token = header.replace("Bearer ","");
            final var auth = getAuthentication(request, token);
            if(!isNull(auth)){
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, String token) {
        if(jwtUtil.tokenValido(token)){
            final var username = jwtUtil.getUsername(token);
            final var user = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        return null;
    }

}
