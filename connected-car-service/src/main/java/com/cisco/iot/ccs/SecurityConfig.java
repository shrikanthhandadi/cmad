package com.cisco.iot.ccs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cisco.iot.ccs.security.CcsUserDetailsService;
import com.cisco.iot.ccs.security.JwtAuthenticationEntryPoint;
import com.cisco.iot.ccs.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CcsUserDetailsService ccsUserDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(ccsUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				// .antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg",
				// "/**/*.jpg", "/**/*.html",
				// "/**/*.css", "/**/*.js")
				// .permitAll()
				// allow swagger resources
				.antMatchers("/swagger-ui.html", "/swagger-resources/**", "/**/api-docs",
						"/webjars/springfox-swagger-ui/**", "/csrf")
				.permitAll()
				// allow authentication related
				.antMatchers("/users/login").permitAll()
				// other requests should be authenticated
				.anyRequest().authenticated();

		// Add our custom JWT security filter
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	public static void main(String[] args) {
		String encode = new BCryptPasswordEncoder().encode("admuser");
		System.out.println(encode);
	}
}