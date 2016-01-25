package com.bionicuniversity.edu.fashiontips.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring Web Security Configuration.
 *
 * @author Maksym Dolia
 * @since 11.12.2015.
 */
@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${api.url.login}")
    private String apiUrlLogin;

    @Value("${api.url.logout}")
    private String apiUrlLogout;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/user/edit").authenticated()
                .antMatchers("/post").authenticated()
                .antMatchers("/post/*").permitAll()
                .and()
                .formLogin().loginPage("/login")
                .failureHandler(customAuthenticationFailureHandler())
                .successHandler(customAuthenticationSuccessHandler())
                .and()
                .logout().logoutSuccessHandler(customLogoutSuccessHandler())
                .deleteCookies("username", "fashionTipsAppToken")
                .and()
                .csrf().disable();
    }

    /* Exclude static resources from security */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/fonts/**")
                .antMatchers("/ng/**")
                .antMatchers("/vendor/**");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider());
    }

    @Bean
    public LogoutSuccessHandler customLogoutSuccessHandler() {

        return new SimpleUrlLogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

                //TODO perform logout on api's side.
                super.onLogoutSuccess(request, response, authentication);
            }
        };
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {

        return new SimpleUrlAuthenticationFailureHandler() {

            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                super.onAuthenticationFailure(request, response, exception);
            }
        };
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.setStatus(HttpServletResponse.SC_OK);
                String sessionId = authentication.getName();

                String header = request.getHeader("x-requested-with");
                if (header != null && header.equals("XMLHttpRequest")) {
                    response.setHeader("Token", sessionId);
                } else {
                    super.onAuthenticationSuccess(request, response, authentication);
                }
            }
        };
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new AuthenticationProvider() {

            @Override
            public Authentication authenticate(Authentication authentication)
                    throws AuthenticationException {
                String name = authentication.getName();
                String password = authentication.getCredentials().toString();

                // use the credentials to try to authenticate against API
                String token;
                if ((token = authenticatedAgainstAPI(name, password)) != null) {
                    List<GrantedAuthority> grantedAuths = new ArrayList<>();
                    return new UsernamePasswordAuthenticationToken(token, password, grantedAuths);
                } else {
                    throw new BadCredentialsException("Unable to auth against API service.");
                }
            }

            private String authenticatedAgainstAPI(String name, String password) {

                /* create Base64 auth header */
                String auth = name + ":" + password;
                byte[] encodedAuth = Base64.encode(auth.getBytes());
                String authHeader = "Basic " + new String(encodedAuth);
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.AUTHORIZATION, authHeader);

                RestTemplate template = new RestTemplate();

                /* make error handler to not return error in case 401 http code */
                template.setErrorHandler(new DefaultResponseErrorHandler() {
                    @Override
                    protected boolean hasError(HttpStatus statusCode) {
                        if (statusCode == HttpStatus.UNAUTHORIZED) {
                            return false;
                        }
                        return super.hasError(statusCode);
                    }
                });

                ResponseEntity<String> responseEntity = template.exchange(apiUrlLogin, HttpMethod.GET, new HttpEntity<>(headers), String.class);
                String token = responseEntity.getHeaders().getFirst("Token");
                return  responseEntity.getStatusCode() == HttpStatus.OK && token != null ?  token : null;
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return authentication.equals(UsernamePasswordAuthenticationToken.class);
            }
        };
    }
}

