package com.socialsync.usersmicroservice.components;

import lombok.Getter;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqConnectionFactoryComponent {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Getter
    @Value("${socialsync.rabbitmq.exchange}")
    private String exchange;

    @Getter
    @Value("${socialsync.rabbitmq.routingkey}")
    private String routingKeyTopics;

    @Getter
    @Value("${socialsync.rabbitmq.routingkey.ids1}")
    private String routingKeyIdsForComments;

    @Getter
    @Value("${socialsync.rabbitmq.routingkey.ids2}")
    private String routingKeyIdsForPosts;

    @Getter
    @Value("${socialsync.rabbitmq.routingkey.ids3}")
    private String routingKeyIdsForTopics;

    @Bean
    private ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(this.host);
        connectionFactory.setUsername(this.username);
        connectionFactory.setPassword(this.password);
        connectionFactory.setPort(this.port);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }
}