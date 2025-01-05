package prog5.assignment.movielist.config;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import prog5.assignment.movielist.service.ui.LoginView;

// configuring spring security extending VaadinWebSecurity sepcifically designed for Vaadin application
@Configuration // marking as configuration class for Spring
@EnableWebSecurity // enabling webSecurity
public class SecurityConfig extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // allowing all users to access public
        http.authorizeHttpRequests(c -> c.requestMatchers(new AntPathRequestMatcher("/public/**"))
                .permitAll());

        super.configure(http);
        // setting custom login view
        this.setLoginView(http, LoginView.class);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        // encoding password
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        // managing user accounts and roles
        // setting up user user with user roles
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("Password111"))
                .roles("USER")
                .build();
        // Setting up admin user with admin and user roles
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("Password111"))
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}


