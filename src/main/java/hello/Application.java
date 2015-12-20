package hello;

import io.undertow.Undertow.Builder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@ServletComponentScan
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
    	final UndertowEmbeddedServletContainerFactory containerFactory = new UndertowEmbeddedServletContainerFactory();
    	containerFactory.addBuilderCustomizers(new UndertowBuilderCustomizer() {
			@Override
			public void customize(Builder builder) {
				String address = containerFactory.getAddress() == null ? "0.0.0.0" : containerFactory.getAddress().getHostAddress();
				builder.addHttpListener(8080, address);
			}
    	});
    	return containerFactory;
    }

    @Bean
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    public WebSecurityConfigurerAdapter securityConfiguration() {
    	return new WebSecurityConfigurerAdapter() {
    		@Override
    		protected void configure(HttpSecurity http) throws Exception {
    			http.authorizeRequests().antMatchers("/secure/**").fullyAuthenticated().anyRequest().permitAll();
    		}
    		
    		@Override
    		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    			auth.inMemoryAuthentication().withUser("admin").password("admin");
    		}
    	};
    }

}