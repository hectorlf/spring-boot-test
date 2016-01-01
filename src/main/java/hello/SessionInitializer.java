package hello;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class SessionInitializer extends AbstractHttpSessionApplicationInitializer {

	public SessionInitializer() {
		super(SessionConfiguration.class);
	}

}