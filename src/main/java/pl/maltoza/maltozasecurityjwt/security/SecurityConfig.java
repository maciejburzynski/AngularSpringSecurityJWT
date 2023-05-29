package pl.maltoza.maltozasecurityjwt.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.maltoza.maltozasecurityjwt.auth.JwtTokenFilter;
import pl.maltoza.maltozasecurityjwt.user.UserService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.headers().frameOptions().disable();

        http.authorizeRequests()
                .antMatchers("/auth").permitAll()
                .antMatchers("/hello-admin").hasAuthority("admin-resource:read")
                .antMatchers("/hello-user").hasAuthority("user-resource:read");

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public PasswordEncoder getBcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email not found: " + username));
    }

    @Bean
    public AuthenticationManager authorizationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
