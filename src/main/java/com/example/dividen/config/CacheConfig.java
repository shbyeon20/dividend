package com.example.dividen.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@RequiredArgsConstructor
@Configuration
public class CacheConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Bean // redis와 connection을 맺는 bean을 생성
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration();
        /* 클러스터 환경으로 설정할 경우 아래의 설정값 사용
        RedisClusterConfiguration redisClusterConfiguration =
         new RedisClusterConfiguration();
         */
        conf.setHostName(host);
        conf.setPort(Integer.parseInt(port));
        // conf.setPassword(); password설정필요시 사용
        return new LettuceConnectionFactory(conf);
    }

    @Bean // redis Connection을 캐쉬에 적용할 수 있는 bean을 생성
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(conf)
                .build();
    }
}
