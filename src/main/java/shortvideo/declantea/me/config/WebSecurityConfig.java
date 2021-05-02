package shortvideo.declantea.me.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.transaction.annotation.Transactional;
import shortvideo.declantea.me.Enum.AuthorityEnum;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@Transactional
@Getter(AccessLevel.PRIVATE)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Value("${REMEMBER_ME_INIT_TABLE}")
    private String db_password;

    @Autowired
    private UserDetailsService userDetailsServiceImpl;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static Logger logger= LoggerFactory.getLogger(WebSecurityConfig.class);


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    }


    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests().antMatchers("/video/upload","/account/videos_manage","/video/{\\w+}/upload")
                // .access("hasAnyAuthority(T(com.edu.neu.project.roles.AuthorityEnum).Manager, T(com.edu.neu.project.roles.AuthorityEnum).Customer)")
                .hasAnyAuthority(AuthorityEnum.Manager.getAuthority(), AuthorityEnum.Customer.getAuthority(), AuthorityEnum.Admin.getAuthority())
                .and()
//                .authorizeRequests().antMatchers("/productmanager/**")
//                .hasAnyAuthority(AuthorityEnum.Manager.getAuthority(), AuthorityEnum.Admin.getAuthority())
//                .and()
//                .authorizeRequests().antMatchers("/customer/**")
//                .hasAuthority(AuthorityEnum.Customer.getAuthority())
//                .and()
//                .authorizeRequests().antMatchers("/admin/**")
//                .hasAuthority(AuthorityEnum.Admin.getAuthority())
//                .and()
                .exceptionHandling().accessDeniedPage("/403");


        httpSecurity.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/spring_security_check")
                .loginPage("/")
                .permitAll()
                .successHandler(new RefererRedirectionAuthenticationSuccessHandler())
                .permitAll()
                .failureUrl("/?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .rememberMe()
//                .rememberMeParameter("remember-me")
//                .key("rem-me-key")
//                .rememberMeCookieName("remember-me-cookie")
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailsServiceImpl)
                .tokenValiditySeconds(1 * 24 * 60 * 60)
//                .tokenValiditySeconds(1 * 60)
                .and().csrf().disable();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setCreateTableOnStartup(Boolean.parseBoolean(db_password));
        db.setDataSource(dataSource);
        return db;
    }


}
