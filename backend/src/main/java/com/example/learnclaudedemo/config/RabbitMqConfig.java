package com.example.learnclaudedemo.config;

import com.example.learnclaudedemo.entity.SysConfig;
import com.example.learnclaudedemo.mapper.SysConfigMapper;
import com.example.learnclaudedemo.mq.MqMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig implements SmartLifecycle {

    private final SysConfigMapper sysConfigMapper;
    private final MqMessageListener mqMessageListener;

    private final List<SimpleMessageListenerContainer> containers = new ArrayList<>();
    private final Map<String, RabbitTemplate> templates = new ConcurrentHashMap<>();

    private final AtomicBoolean running = new AtomicBoolean(false);

    @Override
    public void start() {
        log.info("Starting Dynamic RabbitMQ Manager...");
        List<SysConfig> configs = sysConfigMapper.selectAll();

        if (configs == null || configs.isEmpty()) {
            log.warn("No RabbitMQ configurations found in database.");
            running.set(true);
            return;
        }

        for (SysConfig config : configs) {
            try {
                initializeConnection(config);
            } catch (Exception e) {
                log.error("Failed to initialize RabbitMQ connection for customs_code: {}", config.getCustomsCode(), e);
            }
        }
        running.set(true);
        log.info("Dynamic RabbitMQ Manager started. Active containers: {}", containers.size());
    }

    private void initializeConnection(SysConfig config) {
        log.info("Initializing RabbitMQ for customs_code: {}", config.getCustomsCode());

        // 1. Create Connection Factory
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(config.getHost());
        connectionFactory.setPort(config.getPort() != null ? config.getPort() : 5672);
        connectionFactory.setUsername(config.getUsername());
        connectionFactory.setPassword(config.getPassword());
        connectionFactory.setVirtualHost(config.getVirtualHost() != null ? config.getVirtualHost() : "/");

        // 2. Create Template (for sending)
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        String configKey = config.getCustomsCode() + "_" + config.getCustomsType();
        templates.put(configKey, template);

        // 3. Create Listener Container (for receiving)
        if (config.getQueueName() != null && !config.getQueueName().isEmpty()) {
            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
            container.setConnectionFactory(connectionFactory);
            container.setQueueNames(config.getQueueName());
            container.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    String body = new String(message.getBody(), StandardCharsets.UTF_8);
                    mqMessageListener.handleMessage(body, config.getQueueName());
                }
            });
            // Auto start the container
            container.start();
            containers.add(container);
            log.info("Started listener for queue: {} (customs_code: {})", config.getQueueName(),
                    config.getCustomsCode());
        } else {
            log.warn("No queue configuration for customs_code: {}, listener not started.", config.getCustomsCode());
        }
    }

    @Override
    public void stop() {
        log.info("Stopping Dynamic RabbitMQ Manager...");
        for (SimpleMessageListenerContainer container : containers) {
            if (container.isActive()) {
                container.stop();
            }
        }
        containers.clear();
        templates.clear();
        running.set(false);
    }

    @Override
    public boolean isRunning() {
        return running.get();
    }

    /**
     * Utility to get a template by customs code for sending messages
     */
    public RabbitTemplate getTemplate(String customsCode) {
        return templates.get(customsCode);
    }
}
