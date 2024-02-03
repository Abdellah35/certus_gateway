package com.softedge.solution.config;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.softedge.solution.common.JwtAuthenticationConfig;
import com.softedge.solution.common.JwtTokenAuthenticationFilter;
import com.softedge.solution.common.JwtUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


/**
 * Config role-based auth.
 *
 * @author shuaicj 2017/10/18
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationConfig config;

    @Autowired
    DataSource dataSource;

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        return jdbcUserDetailsManager;
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationConfig jwtConfig() {
        return new JwtAuthenticationConfig();
    }

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
       /* PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser("admin").password(encoder.encode("admin")).roles("ADMIN", "USER").and()
                .withUser("shuaicj").password(encoder.encode("shuaicj")).roles("USER");*/

        auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
                .passwordEncoder(passwordEncoder()).dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .logout().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous()
                .and()
                .exceptionHandling().authenticationEntryPoint(
                (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(config),UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtUsernamePasswordAuthenticationFilter(config, authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)

                // .addFilterAfter(new JwtUsernamePasswordAuthenticationFilter(config, authenticationManager())

                .authorizeRequests()
                .antMatchers("/authenticate","/register").permitAll()
                .antMatchers(config.getUrl()).permitAll()
                .antMatchers("/certus-user-service/api/create-account/**").permitAll()
                .antMatchers("/certus-user-service/api/activate-account/**").permitAll()
                .antMatchers("/certus-user-service/api/forgot-password/**").permitAll()
                .antMatchers("/certus-user-service/api/resend-otp/**").permitAll()
                .antMatchers("/certus-user-service/api/user-otp/**").permitAll()
                .antMatchers("/certus-user-service/api/user-otp/**").permitAll()
                .antMatchers("/certus-user-service/api/login/**").permitAll()
                .antMatchers("/certus-user-service/api/digital/ipv-info/**").hasAnyRole("USER","ADMIN")

                .antMatchers("/certus-user-service/api/admin/**").hasRole("ADMIN")
                .antMatchers("/certus-user-service/api/company-info/**").hasRole("USER")

                .antMatchers("/certus-user-service/api/tmp-create-user/**").hasRole("CLIENT")
                .antMatchers("/certus-user-service/api/client/generate-user-details-data-csv/**").hasRole("CLIENT")

                .antMatchers("/certus-kyc-documents-service/api/kyc/company/documents/request-document/**").hasRole("CLIENT")

                .antMatchers("/certus-user-service/api/user/**").hasAnyRole("USER","CLIENT","ADMIN")
                .antMatchers("/certus-user-service/api/user/user-image/**").hasAnyRole("USER","CLIENT","ADMIN")
                .antMatchers("/certus-user-service/api/digital/**").hasAnyRole("USER","ADMIN")
                .antMatchers("/certus-user-service/api/digital/ipv-info/**").permitAll()
                .antMatchers("/certus-user-service/api/reset-password/**").hasAnyRole("USER","CLIENT","ADMIN")
                .antMatchers("/certus-user-service/greet/**").hasRole("ADMIN")

                .antMatchers("/certus-user-service/wish/**").hasRole("USER")
                .antMatchers("/certus-user-service/api/notification/**").hasAnyRole("USER","CLIENT","ADMIN")
                .antMatchers("/certus-user-service/api/comment/**").hasAnyRole("USER","CLIENT","ADMIN")

                .antMatchers("/certus-kyc-documents-service/api/kyc/user/documents/attachment-image/**").hasAnyRole("USER","CLIENT","ADMIN")
                .antMatchers("/certus-kyc-documents-service/api/kyc/documents/**").hasAnyRole("USER","CLIENT","ADMIN")
                .antMatchers("/certus-kyc-documents-service/api/kyc/documents/get-document-meta/**").hasRole("CLIENT")
                .antMatchers("/certus-kyc-documents-service/api/kyc/documents/admin/meta-load").hasRole("ADMIN")
                .antMatchers("/certus-kyc-documents-service/api/kyc/documents/feign-client/redis/user-ipv").hasAnyRole("USER","ADMIN")
                .antMatchers("/certus-kyc-documents-service/api/kyc/company/documents/user/**").hasRole("CLIENT")
                .antMatchers("/certus-kyc-documents-service/api/kyc/user/documents/**").hasAnyRole("USER","CLIENT")

                .anyRequest().authenticated().and().cors();
    }

  /*  @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/*", configuration);
        return source;
    }*/
}

