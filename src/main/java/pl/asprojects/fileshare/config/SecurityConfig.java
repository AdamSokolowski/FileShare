package pl.asprojects.fileshare.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import pl.asprojects.fileshare.service.SpringDataUserDetailsService;





@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService securityUserDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SpringDataUserDetailsService customUserDetailsService() {
    	return new SpringDataUserDetailsService();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(securityUserDetailsService);
        //provider.setPasswordEncoder(new BCryptPasswordEncoder);
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf().disable()
                .authorizeRequests().antMatchers("/index", "/user/register", "/user/nowlogged").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/user/login").permitAll()
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/").permitAll();
    }


    /*
    @Override
    protected UserDetailsService userDetailsService() {
        List<UserDetails> users = new ArrayList<>();
        users.add(User.withDefaultPasswordEncoder().username("Admin").password("pass").roles("USER").build());

        return new InMemoryUserDetailsManager(users);
    }
   */
}
