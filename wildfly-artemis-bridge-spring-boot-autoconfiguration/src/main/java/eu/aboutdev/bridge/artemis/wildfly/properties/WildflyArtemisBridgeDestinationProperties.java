package eu.aboutdev.bridge.artemis.wildfly.properties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.wildfly.artemis.bridge")
public class WildflyArtemisBridgeDestinationProperties {

	private Map<String, Destination> destination = new HashMap<>();
	
	public Map<String, Destination> getDestination() {
		return destination;
	}

	public static class Destination {
		
		private String source;
		
		private String target;
		
		private String user;
		
		private String password;

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getTarget() {
			return target;
		}

		public void setTarget(String target) {
			this.target = target;
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
