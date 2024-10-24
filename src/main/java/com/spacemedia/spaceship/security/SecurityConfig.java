package com.spacemedia.spaceship.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.GET,"/spaceships/**").hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.PUT,"/spaceships/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/spaceships/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/spaceships/**").hasRole("ADMIN")
                        .requestMatchers("/swagger-ui/**").hasAnyRole("ADMIN","USER")
                        .requestMatchers("/h2-console/**").hasAnyRole("ADMIN","USER")

                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());

        httpSecurity.csrf((csrf) -> csrf.ignoringRequestMatchers("/spaceships/**")
                .ignoringRequestMatchers("/swagger-ui/**")
                .ignoringRequestMatchers("/h2-console/**"));

        return httpSecurity.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        UserDetails userAdmin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, userAdmin);
    }
}
