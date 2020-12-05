package eu.aboutdev.bridge.artemis.wildfly.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import eu.aboutdev.bridge.artemis.wildfly.properties.WildflyArtemisBridgeDestinationProperties;
import eu.aboutdev.bridge.artemis.wildfly.properties.WildflyArtemisBridgeRemoteListenerProperties;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(EnableWildflyArtemisBridge.class)
@EnableConfigurationProperties({ WildflyArtemisBridgeRemoteListenerProperties.class,
		WildflyArtemisBridgeDestinationProperties.class })
@Import({ WildflyArtemisBridgeAutoconfigurationListenerConfiguration.class,
		WildflyArtemisBridgeAutoconfigurationDestinationConfiguration.class })
public class WildflyArtemisBridgeAutoconfiguration {

}
