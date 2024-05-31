package pfe.serveur.partie_serveur.configutartion;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pfe.serveur.partie_serveur.filter.AuthFilter;
import pfe.serveur.partie_serveur.utilisateur.service.CustomSuccessHandler;
import pfe.serveur.partie_serveur.utilisateur.service.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    CustomSuccessHandler customSuccessHandler;

    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    private AuthFilter jwtRequestFilter;
    @Bean
    public static PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception{
        http.csrf(c -> c.disable())

                .authorizeHttpRequests(request -> request
                        .requestMatchers("/", "/homepage","/favicon.ico").permitAll()
                        .requestMatchers("/admin-page").hasAuthority("ADMIN")
                        .requestMatchers("/user-page").hasAuthority("USER")
                        .requestMatchers("/user").hasAuthority("USER")
                        .requestMatchers("/sign-up","/users/{id}","/users", "/css/**", "/Img/**", "/bootstrap-5.3.3-dist/css/bootstrap.min.css","/material-icon/**"
                                ,"/path/to/fontawesome-all.min.css").permitAll()
                        .anyRequest().authenticated())


                .formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login")
                        .successHandler(customSuccessHandler).permitAll())

                .logout(form -> form.invalidateHttpSession(true).clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout").permitAll())
                        .addFilterBefore(jwtRequestFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Autowired
    public void configure (AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

}
