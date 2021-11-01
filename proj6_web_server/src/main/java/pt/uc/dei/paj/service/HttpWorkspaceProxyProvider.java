package pt.uc.dei.paj.service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import pt.uc.dei.paj.interfaces.WorkspaceServicesInterface;
import pt.uc.dei.paj.util.Constants;

@RequestScoped
public class HttpWorkspaceProxyProvider {

	private WorkspaceServicesInterface proxy;

	@PostConstruct
	public void init() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(UriBuilder.fromPath(Constants.PATH));
		proxy = target.proxy(WorkspaceServicesInterface.class);	
	}
	
	public WorkspaceServicesInterface getProxy() {
		return proxy;
	}

	public void setProxy(WorkspaceServicesInterface proxy) {
		this.proxy = proxy;
	}
}
