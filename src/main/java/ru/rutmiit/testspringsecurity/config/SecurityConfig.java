package ru.rutmiit.testspringsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.rutmiit.testspringsecurity.services.ClientsDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ClientsDetailsService clientsDetailsService;

    @Autowired
    public SecurityConfig(ClientsDetailsService clientsDetailsService) {
        this.clientsDetailsService = clientsDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(clientsDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // Отключение защиты от меж-сайтовой подделки запросов
                .authorizeRequests() // Все запросы теперь проходят через авторизацию
                .antMatchers("/auth/login", "/auth/reg", "/error").permitAll() // Кроме этих
                .anyRequest().authenticated() // Для всех других запросов нужна аутентификация
                .and()
                .formLogin().loginPage("/auth/login") // html форма для логина
                .loginProcessingUrl("/process_login") // url адрес для логина
                .defaultSuccessUrl("/", true)
                .failureUrl("/auth/login?error");
    }

    @Bean
    PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
