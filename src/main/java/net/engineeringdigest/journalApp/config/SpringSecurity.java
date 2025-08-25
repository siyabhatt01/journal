//package net.engineeringdigest.journalApp.config;
//
//import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SpringSecurity extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    UserDetailsServiceImpl userDetailsService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//       http.authorizeRequests()
//               .antMatchers("/journal/**","/user/**").authenticated()
//               .anyRequest().permitAll()
//               .and()
//               .httpBasic();
//       http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//               .and()
//               .csrf().disable();
//    }
//
//    //for authorizing user checking their details from userDetailServiceImpl and checking pwd
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder()
//    {
//        return new BCryptPasswordEncoder();
//    }
//}
//
//
////steps to perform to enable authentication
////sending username and pwd in header along with req
//
////1.A user entity to represent user data model.
////2.a repo userRepository to interact with mongoDb
////3. UserDetailService implementation to fetch user detail.(It is reqd for spring security, an interface in spring security).
////4.a config SecurityConfig to integrate everything with spring security.
package net.engineeringdigest.journalApp.config;

import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/journal/**","/user/**").authenticated()
                .antMatchers("/admin").hasRole("ADMIN")
                .anyRequest().permitAll() // Allow ALL requests
                .and()
                .httpBasic(); // Keep httpBasic just in case, though not needed for permitAll
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable();
    }

    // This part is for authentication, but won't be hit if anyRequest().permitAll() works
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}