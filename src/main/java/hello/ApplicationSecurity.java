package hello;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.SecurityProperties.Headers;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

	private static final String[] MANAGEMENT_ENDPOINTS = {"/management/info"};

	@Bean
	@ConditionalOnMissingBean
	public SecurityProperties securityProperties() {
		return new SecurityProperties();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// general properties
		SecurityProperties props = securityProperties();
		if (props.isRequireSsl()) http.requiresChannel().anyRequest().requiresSecure();
		if (!props.isEnableCsrf()) http.csrf().disable();
		if (!props.getHeaders().isFrame()) http.headers().frameOptions().disable();
		if (!props.getHeaders().isContentType()) http.headers().contentTypeOptions().disable();
		if (!props.getHeaders().isXss()) http.headers().xssProtection().disable();
		if (props.getHeaders().getHsts() != Headers.HSTS.NONE) http.headers().httpStrictTransportSecurity().includeSubDomains(props.getHeaders().getHsts() == Headers.HSTS.ALL);
		http.sessionManagement().sessionCreationPolicy(props.getSessions());
		// management access rules
		http.requiresChannel().antMatchers(MANAGEMENT_ENDPOINTS).requiresSecure();
		http.authorizeRequests().antMatchers(MANAGEMENT_ENDPOINTS).hasRole("ADMIN").and().httpBasic();
		// app access rules
		http.authorizeRequests().antMatchers("/secure/**").hasRole("USER").and().httpBasic();
		// default access rules
		http.authorizeRequests().antMatchers("/**").permitAll().and().httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER")
			.and().withUser("admin").password("admin").roles("ADMIN");
	}

}