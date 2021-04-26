package shortvideo.declantea.me.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import shortvideo.declantea.me.dao.AuthorizationDAO;
import shortvideo.declantea.me.security.authority.AuthorityEnum;

@Configuration
@EnableWebSecurity
@Transactional
@Getter(AccessLevel.PRIVATE)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsServiceImpl;
    private AuthorizationDAO authorizationDAO;

    @Autowired
    public void setRememberMeDAO(AuthorizationDAO authorizationDAO) {
        this.authorizationDAO = authorizationDAO;
    }

    @Autowired
    public void setUserDetailsServiceImpl(UserDetailsService userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsServiceImpl);
    }


    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests().antMatchers("/account/**")
                // .access("hasAnyAuthority(T(com.edu.neu.project.roles.AuthorityEnum).Manager, T(com.edu.neu.project.roles.AuthorityEnum).Customer)")
                .hasAnyAuthority(AuthorityEnum.Manager.getAuthority(), AuthorityEnum.Customer.getAuthority(), AuthorityEnum.Admin.getAuthority())
                .and()
                .authorizeRequests().antMatchers("/productmanager/**")
                .hasAnyAuthority(AuthorityEnum.Manager.getAuthority(), AuthorityEnum.Admin.getAuthority())
                .and()
                .authorizeRequests().antMatchers("/customer/**")
                .hasAuthority(AuthorityEnum.Customer.getAuthority())
                .and()
                .authorizeRequests().antMatchers("/admin/**")
                .hasAuthority(AuthorityEnum.Admin.getAuthority())
                .and()
                .exceptionHandling().accessDeniedPage("/403");


        httpSecurity.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/spring_security_check")
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/listproduct")
                .permitAll()
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                .and().csrf().disable();
    }


}
