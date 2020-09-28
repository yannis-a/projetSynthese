package fr.florian.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final RESTAuthenticationEntryPoint authenticationEntryPoint;
    private final RESTAuthenticationSuccessHandler authenticationSuccessHandler;
    private final RESTAuthenticationFailureHandler authenticationFailureHandler;

    public WebSecurityConfig(final RESTAuthenticationEntryPoint authenticationEntryPoint, final RESTAuthenticationSuccessHandler authenticationSuccessHandler, final RESTAuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();

        http.cors();
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/h2/**").permitAll()
                .anyRequest().fullyAuthenticated();
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
        http.formLogin().successHandler(authenticationSuccessHandler);
        http.formLogin().failureHandler(authenticationFailureHandler);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:8389/dc=banquemoselle,dc=org")
                .and()
                .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");
    }

    @Component
    static class RESTAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(final HttpServletRequest request, final HttpServletResponse response,
                             final AuthenticationException authException) throws IOException {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Component
    static class RESTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                            final Authentication authentication) throws IOException {
            clearAuthenticationAttributes(request);
            response.getWriter().write(new ObjectMapper().writeValueAsString(authentication.getPrincipal()));
            response.setStatus(OK.value());
        }
    }

    @Component
    static class RESTAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
                                            final AuthenticationException exception) throws IOException, ServletException {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
