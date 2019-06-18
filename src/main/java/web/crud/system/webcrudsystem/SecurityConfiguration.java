package web.crud.system.webcrudsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
/*
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests() 
        .antMatchers("/register").permitAll()      
        .antMatchers("/login").permitAll()
        .antMatchers("/console/**").permitAll();

    http.csrf().disable();
    http.headers().frameOptions().disable();

  }*/


/*

  @Bean
  @Override
  protected UserDetailsService userDetailsService() {

    List<UserDetails> userDetails = new ArrayList<>();
    userDetails.add(User.withDefaultPasswordEncoder().username("rohan").password("1234").roles("USER").build());
    return new InMemoryUserDetailsManager(userDetails);

  }
*/



  @Autowired
  private DataSource dataSource;

  @Value("${spring.queries.users-query}")
  private String usersQuery;

  @Value("${spring.queries.roles-query}")
  private String rolesQuery;

  @Override
  protected void configure(AuthenticationManagerBuilder auth)
          throws Exception {


/*      first way test it in memoryathtentication
          auth.inMemoryAuthentication()
                  .withUser("admin").password(passwordEncoder().encode("1234")).roles("ADMIN")
                  .and()
                  .withUser("teacher").password(passwordEncoder().encode("1234")).roles("TEACHER");
*/


            auth.
            jdbcAuthentication()
            .authoritiesByUsernameQuery(rolesQuery)
            .usersByUsernameQuery(usersQuery)
            .dataSource(dataSource)
            .passwordEncoder(passwordEncoder());

  }


  @Override
  protected void configure(final HttpSecurity http) throws Exception {
      // in memory testing
 /*         http.
          authorizeRequests()
          .antMatchers("/admin/**").authenticated()
          .antMatchers("/posts/**").hasRole("ADMIN")
          .antMatchers("/teacher").hasRole("TEACHER")
                  .anyRequest().permitAll()
                  .and()
           .httpBasic();*/



             http
            .authorizeRequests()
            .antMatchers("/login").permitAll()
            .antMatchers("/register").permitAll()
            .antMatchers("/posts").authenticated()
            .antMatchers("/admin/**").hasAuthority("ADMIN")
            .antMatchers("/").permitAll()
            .and().csrf().disable().formLogin()
            .loginPage("/login").failureUrl("/login?error=true")
            .defaultSuccessUrl("/posts")
            .usernameParameter("email")
            .passwordParameter("password")
            .and().logout()

            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

                     .logoutSuccessUrl("/").and().exceptionHandling()

                     .accessDeniedPage("/access-denied")
            .and()
            .httpBasic();




  }

  @Override
  public void configure(WebSecurity web) throws Exception {
            web
            .ignoring()
            .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");

  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}