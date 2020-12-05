package eu.aboutdev.bridge.artemis.wildfly.properties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.wildfly.artemis.bridge")
public class WildflyArtemisBridgeRemoteListenerProperties {
	
	private String test;

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	private Map<String, Listener> listener = new HashMap<>();
	
	public Map<String, Listener> getRemoteListener() {
		return listener;
	}

	public static class Listener {
		
		private String name;
		
		private String host;
		
		private int port;
		
		private String user;
		
		private String password;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
	}
}
