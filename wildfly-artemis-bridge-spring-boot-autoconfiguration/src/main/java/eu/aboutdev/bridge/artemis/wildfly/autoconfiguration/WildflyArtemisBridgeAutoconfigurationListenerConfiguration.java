package eu.aboutdev.bridge.artemis.wildfly.autoconfiguration;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.jms.ConnectionFactory;

import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
import org.apache.activemq.artemis.core.remoting.impl.netty.TransportConstants;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMultiCandidate;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.util.StringUtils;

import eu.aboutdev.bridge.artemis.wildfly.properties.WildflyArtemisBridgeRemoteListenerProperties;
import eu.aboutdev.bridge.artemis.wildfly.properties.WildflyArtemisBridgeRemoteListenerProperties.Listener;

@Configuration(proxyBeanMethods = false)
public class WildflyArtemisBridgeAutoconfigurationListenerConfiguration {

	private final WildflyArtemisBridgeRemoteListenerProperties properties;

	private final ListableBeanFactory beanFactory;

	WildflyArtemisBridgeAutoconfigurationListenerConfiguration(WildflyArtemisBridgeRemoteListenerProperties properties,
			ListableBeanFactory beanFactory) {
		this.properties = properties;
		this.beanFactory = beanFactory;
	}
	
	@Bean
	@ConditionalOnMultiCandidate(ConnectionFactory.class)
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
			DefaultJmsListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		return factory;
	}

	@Bean
	public List<ActiveMQConnectionFactory> connectionFactories() {
		final Map<String, Listener> remoteListener = this.properties.getRemoteListener();
		final List<String> keyList = remoteListener.entrySet().stream().filter(entry -> entry.getKey() != null)
				.map(entry -> entry.getKey()).collect(Collectors.toList());
		final List<ActiveMQConnectionFactory> connectionFactoryList = new ArrayList<>();
		keyList.stream().forEach(key -> {
			try {
				final ActiveMQConnectionFactory activeMQConnectionFactory = createConnectionFactory(
						ActiveMQConnectionFactory.class, remoteListener.get(key));
				connectionFactoryList.add(activeMQConnectionFactory);
			} catch (Exception e) {
				e.printStackTrace();
			}

		});

		return connectionFactoryList;
	}

	private <T extends ActiveMQConnectionFactory> T createConnectionFactory(Class<T> factoryClass,
			Listener listener) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put(TransportConstants.HOST_PROP_NAME, listener.getHost());
		params.put(TransportConstants.PORT_PROP_NAME, listener.getPort());
		TransportConfiguration transportConfiguration = new TransportConfiguration(
				NettyConnectorFactory.class.getName(), params);
		Constructor<T> constructor = factoryClass.getConstructor(boolean.class, TransportConfiguration[].class);
		T connectionFactory = constructor.newInstance(false, new TransportConfiguration[] { transportConfiguration });
		String user = listener.getUser();
		if (StringUtils.hasText(user)) {
			connectionFactory.setUser(user);
			connectionFactory.setPassword(listener.getPassword());
		}
		return connectionFactory;
	}
}
