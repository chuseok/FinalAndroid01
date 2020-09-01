package org.dragon.listener;

import java.util.List;

import org.dragon.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;

import lombok.Setter;

public class SessionDestoryListener implements ApplicationListener<SessionDestroyedEvent> {
	@Setter(onMethod_ = { @Autowired})
	private MainMapper mapper;
	
	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {
		// TODO Auto-generated method stub
		List<SecurityContext> contexts = event.getSecurityContexts();
		if (contexts.isEmpty() == false) {
			for (SecurityContext ctx : contexts) {
				System.out.println("update date!!");
				mapper.updateDate(ctx.getAuthentication().getName());
			}
			
		}
	}

}
