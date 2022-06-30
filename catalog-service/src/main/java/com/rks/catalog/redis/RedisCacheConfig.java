package com.rks.catalog.redis;import org.springframework.beans.factory.annotation.Value;import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;import org.springframework.cache.annotation.EnableCaching;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;import org.springframework.data.redis.cache.RedisCacheConfiguration;import org.springframework.data.redis.cache.RedisCacheManager;import org.springframework.data.redis.connection.RedisConnectionFactory;import org.springframework.data.redis.connection.RedisStandaloneConfiguration;import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;import org.springframework.data.redis.core.RedisTemplate;import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;import org.springframework.data.redis.serializer.RedisSerializationContext;import org.springframework.data.redis.serializer.StringRedisSerializer;import java.time.Duration;import java.util.Collections;@EnableCaching@Configurationpublic class RedisCacheConfig {    @Value("${order-service.redis.host:localhost}")    private String REDIS_HOST;    @Value("${order-service.redis.port:6379}")    private int REDIS_PORT;    @Bean    public RedisCacheConfiguration cacheConfiguration() {        return RedisCacheConfiguration.defaultCacheConfig()                .entryTtl(Duration.ofMinutes(60))                .disableCachingNullValues()                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(                        new GenericJackson2JsonRedisSerializer()));    }    @Bean    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {        return (builder) -> builder                .withCacheConfiguration("categoryCache",                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))                .withCacheConfiguration("productCache",                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)));    }}