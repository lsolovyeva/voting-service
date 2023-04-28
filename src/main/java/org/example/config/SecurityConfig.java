package org.example.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity(debug = true)
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMethodSecurity(securedEnabled = true) // Add this

@Slf4j
@AllArgsConstructor
public class SecurityConfig {

    public static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private final UserRepository userRepository;

    //private final RestAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PASSWORD_ENCODER;
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            log.debug("Authenticating '{}'", email);
            Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(email);
            return new AuthUser(optionalUser.orElseThrow(
                    () -> new UsernameNotFoundException("User '" + email + "' was not found")));
        };
    }*/

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            log.debug("Authenticating '{}'", email);
            Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(email);
            return new User(optionalUser.orElseThrow(
                    () -> new UsernameNotFoundException("User '" + email + "' was not found")));
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                //.requestMatchers("/h2-console/**").permitAll()
                .requestMatchers(antMatcher("/h2-console/**")).permitAll()

                .requestMatchers("/api/admin/**").hasRole(Role.ADMIN.name())
                .requestMatchers("/api/user/**").hasRole(Role.USER.name())
                .requestMatchers("/api/view/**").permitAll() //.hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                //.requestMatchers("/api/**").authenticated()

                .and().exceptionHandling().accessDeniedPage("/api/view/403") //BAD IDEA, bumps into infinite loop
                //.and().formLogin()
                //.and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true)
                //.requestMatchers(HttpMethod.GET, "/api/admin/restaurants").hasRole(Role.ADMIN.name())
                //.requestMatchers(POST, "/api/admin/restaurants/1/new").hasRole(Role.ADMIN.name())
                //.requestMatchers(POST, "/api/admin/restaurants/**").hasRole(Role.ADMIN.name())
                .and()
                // .addFilterAfter(new JWTAuthorizationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))), AccessDeniedExceptionFilter.class)

                //.requestMatchers("/api/**").permitAll()
                //.requestMatchers(HttpMethod.POST, "/api/profile").anonymous()
                .httpBasic()
                //.authenticationEntryPoint(authenticationEntryPoint)
                .and()
                //.addFilterAfter(customFilter, AccessDeniedExceptionFilter.class)
                //.addFilterBefore(customFilter, DisableEncodeUrlFilter.class)

                //.addFilter(customFilter)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().ignoringRequestMatchers(toH2Console()).disable()
                .headers().frameOptions().disable(); // https://stackoverflow.com/questions/53395200/h2-console-is-not-showing-in-browser
        //http.addFilterAfter(new JWTAuthorizationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))), AccessDeniedExceptionFilter.class);
       //http.addFilter(new AccessDeniedExceptionFilter());
        return http.build();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**");
    }
}
