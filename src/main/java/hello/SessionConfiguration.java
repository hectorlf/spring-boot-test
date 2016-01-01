package hello;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.session.web.http.MultiHttpSessionStrategy;
import org.springframework.session.web.http.SessionRepositoryFilter;

@Configuration
public class SessionConfiguration {

	private CookieHttpSessionStrategy defaultHttpSessionStrategy = new CookieHttpSessionStrategy();

	private HttpSessionStrategy httpSessionStrategy = defaultHttpSessionStrategy;

	private ServletContext servletContext;

	@Bean
	public <S extends ExpiringSession> SessionRepositoryFilter<? extends ExpiringSession> springSessionRepositoryFilter(SessionRepository<S> sessionRepository) {
		SessionRepositoryFilter<S> sessionRepositoryFilter = new SessionRepositoryFilter<S>(sessionRepository);
		sessionRepositoryFilter.setServletContext(servletContext);
		if(httpSessionStrategy instanceof MultiHttpSessionStrategy) {
			sessionRepositoryFilter.setHttpSessionStrategy((MultiHttpSessionStrategy) httpSessionStrategy);
		} else {
			sessionRepositoryFilter.setHttpSessionStrategy(httpSessionStrategy);
		}
		return sessionRepositoryFilter;
	}

	@Bean
	public SessionRepository<ExpiringSession> sessionRepository() {
		return new MapSessionRepository();
	}

	@Autowired(required=false)
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Autowired(required = false)
	public void setHttpSessionStrategy(HttpSessionStrategy httpSessionStrategy) {
		this.httpSessionStrategy = httpSessionStrategy;
	}

}