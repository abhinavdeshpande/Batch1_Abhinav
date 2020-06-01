package com.cg;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
//@EnableOAuth2Sso
public class MyWebSecurity extends WebSecurityConfigurerAdapter {

	public MyWebSecurity() {
		System.out.println("inside constructor of MyWebSecurity");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("inside configureGlobal of SecurityConfig");
		auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
		
		/*
		 * http.csrf().disable().antMatcher("/**").authorizeRequests().antMatchers("/",
		 * "/product-cart/cart/{name}", "/index.html")
		 * .permitAll().anyRequest().authenticated();
		 */
		 
	}

}
