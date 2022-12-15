package workshop.day28marvelworkshop.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class AppConfig {
    
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;

    @Value("${spring.redis.user}")
    private String redisUser;

    @Value("${spring.redis.database}")
    private int redisDatabase;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean
    @Scope("singleton")
    public RedisTemplate<String, String> redisTemplate() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort.get());
        config.setDatabase(redisDatabase);
        config.setUsername(redisUser);
        config.setPassword(redisPassword);

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFactory = new JedisConnectionFactory(config, jedisClient);
        jedisFactory.afterPropertiesSet();

        final RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}
