package com.socialsync.postsmicroservice.components;

import lombok.Getter;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqConnectionFactoryComponentImages {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Getter
    @Value("${socialsync.rabbitmq.exchange.n}")
    private String exchange;

    @Getter
    @Value("${socialsync.rabbitmq.routingkey.i}")
    private String routingKey;

    @Bean
    private ConnectionFactory imagesConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(this.host);
        connectionFactory.setUsername(this.username);
        connectionFactory.setPassword(this.password);
        connectionFactory.setPort(this.port);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplateImages() {  // Update the method name
        return new RabbitTemplate(imagesConnectionFactory());
    }
}
