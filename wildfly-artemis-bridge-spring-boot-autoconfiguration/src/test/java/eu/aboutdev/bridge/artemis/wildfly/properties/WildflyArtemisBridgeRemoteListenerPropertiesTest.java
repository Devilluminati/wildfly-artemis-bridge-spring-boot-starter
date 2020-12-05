package eu.aboutdev.bridge.artemis.wildfly.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import eu.aboutdev.bridge.artemis.wildfly.autoconfiguration.WildflyArtemisBridgeAutoconfiguration;
import eu.aboutdev.bridge.artemis.wildfly.properties.WildflyArtemisBridgeRemoteListenerProperties.Listener;

@SpringBootTest(classes = WildflyArtemisBridgeAutoconfiguration.class)
@EnableConfigurationProperties(value = WildflyArtemisBridgeRemoteListenerProperties.class)
@ActiveProfiles("test")
class WildflyArtemisBridgeRemoteListenerPropertiesTest {

	@Autowired
	private WildflyArtemisBridgeRemoteListenerProperties properties;

	@Test
	public void givenRemoteListenerConfiguration_whenIsSetRight_thenShouldBeEquals() {
		// given
		Map<String, Listener> remoteListener = properties.getRemoteListener();

		// when
		Listener listener = remoteListener.get("test");

		// then
		assertThat(listener.getName(), is("test"));
		assertThat(listener.getHost(), is("localhost"));
		assertThat(listener.getPort(), is(8080));
		assertThat(listener.getUser(), is("test"));
		assertThat(listener.getPassword(), is("test"));
	}
}
