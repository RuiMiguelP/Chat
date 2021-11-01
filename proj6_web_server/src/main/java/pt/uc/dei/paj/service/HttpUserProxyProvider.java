package pt.uc.dei.paj.service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import pt.uc.dei.paj.interfaces.UserServicesInterface;
import pt.uc.dei.paj.util.Constants;

@RequestScoped
public class HttpUserProxyProvider {

	private UserServicesInterface proxy;

	@PostConstruct
	public void init() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(UriBuilder.fromPath(Constants.PATH));
		proxy = target.proxy(UserServicesInterface.class);	
	}
	
	public UserServicesInterface getProxy() {
		return proxy;
	}

	public void setProxy(UserServicesInterface proxy) {
		this.proxy = proxy;
	}
}
