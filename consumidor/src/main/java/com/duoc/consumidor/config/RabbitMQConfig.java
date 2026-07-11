package com.duoc.consumidor.config;

import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

@Configuration
public class RabbitMQConfig {

	public static final String GUIA_QUEUE = "guia.despacho.queue";
	public static final String GUIA_EXCHANGE = "guia.despacho.exchange";
	public static final String GUIA_ROUTING_KEY = "guia.despacho";

	public static final String GUIA_DLQ = "guia.error.queue";
	public static final String GUIA_DLX = "guia.error.exchange";
	public static final String GUIA_DLX_ROUTING_KEY = "guia.error";

	@Value("${spring.rabbitmq.host}")
	private String host;

	@Value("${spring.rabbitmq.port}")
	private int port;

	@Value("${spring.rabbitmq.username}")
	private String username;

	@Value("${spring.rabbitmq.password}")
	private String password;

	@Bean
	MessageConverter messageConverter() {
		return new SimpleMessageConverter();
	}

	@Bean
	CachingConnectionFactory connectionFactory() {

		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setHost(host);
		factory.setPort(port);
		factory.setUsername(username);
		factory.setPassword(password);
		return factory;
	}

	//cola principal
	@Bean
	Queue guiaQueue() {

		return new Queue(GUIA_QUEUE, true, false, false,
				Map.of(
					"x-dead-letter-exchange", GUIA_DLX,
					"x-dead-letter-routing-key", GUIA_DLX_ROUTING_KEY
				)
		);
	}

	@Bean
	DirectExchange guiaExchange() {

		return new DirectExchange(GUIA_EXCHANGE);
	}

	//cola de errores
	@Bean
	Queue guiaErrorQueue() {
		return new Queue(GUIA_DLQ,true);
	}

	//dead letter exchange
	@Bean
	DirectExchange guiaErrorExchange() {
		return new DirectExchange(GUIA_DLX);
	}

	//binding principal
	@Bean
	Binding binding(Queue guiaQueue, DirectExchange guiaExchange) {

		return BindingBuilder.bind(guiaQueue).to(guiaExchange).with(GUIA_ROUTING_KEY);
	}

	//binding de errores/dql
	@Bean
	Binding dlqBinding() {
		return BindingBuilder
				.bind(guiaErrorQueue())
				.to(guiaErrorExchange())
				.with(GUIA_DLX_ROUTING_KEY);
	}

	@Bean
	RabbitTemplate rabbitTemplate(
			ConnectionFactory connectionFactory,
			MessageConverter messageConverter) {

		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

		rabbitTemplate.setMessageConverter(messageConverter);

		return rabbitTemplate;
	}
}
