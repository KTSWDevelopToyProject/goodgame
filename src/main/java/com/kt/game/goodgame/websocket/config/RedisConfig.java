package com.kt.game.goodgame.websocket.config;

import com.kt.game.goodgame.innergame.service.StompSocketService;
import com.kt.game.goodgame.websocket.service.RedisSubscriber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

// 1. https://velog.io/@rainbowweb/Redis-%EC%97%B0%EC%8A%B5-2-pubsub-%EA%B5%AC%EC%84%B1
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private String redisPort;


    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(Integer.parseInt(redisPort));
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        return lettuceConnectionFactory;
    }
//
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessage.class));
////        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        return redisTemplate;
//    }
//
//    //리스너어댑터 설정
//    @Bean
//    MessageListenerAdapter messageListenerAdapter() {
//        return new MessageListenerAdapter(new RedisSubService());
//    }
//
//    //컨테이너 설정
//    @Bean
//    RedisMessageListenerContainer redisContainer() {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(redisConnectionFactory());
//        container.addMessageListener(messageListenerAdapter(), topic());
//        return container;
//    }
//
//    //pub/sub 토픽 설정
//    @Bean
//    ChannelTopic topic() {
//        return new ChannelTopic("topic1");
//    }
//
// 1. end


// 2. https://velog.io/@ohjinseo/WebSocket-Spring-Boot-stomp-Redis-PubSub-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%B1%84%ED%8C%85-%EA%B5%AC%ED%98%84
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer( // (1)
                                                                        RedisConnectionFactory connectionFactory,
                                                                        MessageListenerAdapter listenerAdapter,
                                                                        ChannelTopic channelTopic
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, channelTopic);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(StompSocketService subscriber) { // (2)
        return new MessageListenerAdapter(subscriber, "onMessage");
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate
            (RedisConnectionFactory connectionFactory) { // (3)
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return redisTemplate;
    }

    @Bean
    public ChannelTopic channelTopic() { // (4)
        return new ChannelTopic("topic1");
    }
// 2. end


}
