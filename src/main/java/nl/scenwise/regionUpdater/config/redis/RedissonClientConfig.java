package nl.scenwise.regionUpdater.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RedissonClientConfig {

    /**
     * <p>
     * Creates a Redisson {@link Config} using {@link RedisProperties}.
     * </p>
     * @param redisProperties to use.
     * @return {@link RedissonClient}
     */
    public static Config createConfig(RedisProperties redisProperties) {
        String protocol = redisProperties.getSsl() ? "rediss" : "redis";

        if (!redisProperties.getSsl()) {
            log.warn("Redisson client SSL is not enabled.");
        }
        int port = redisProperties.getPort();
        String address = String.format("%s://%s:%s@%s:%s", protocol, redisProperties.getUsername(),
                redisProperties.getPassword(), redisProperties.getHost(), port);
        System.out.println(address);
        Config config = new Config();

        config.useSingleServer()
                .setAddress(address)
                .setConnectionMinimumIdleSize(redisProperties.getConnectionMinIdleSize() != null ? redisProperties.getConnectionMinIdleSize() : 64)
                .setConnectionPoolSize(redisProperties.getConnectionPoolSize() != null ? redisProperties.getConnectionPoolSize() : 64)
                .setSubscriptionConnectionPoolSize(redisProperties.getSubConnectionPoolSize() != null ? redisProperties.getSubConnectionPoolSize() : 64)
                .setSubscriptionConnectionMinimumIdleSize(redisProperties.getSubConnectionMinIdleSize() != null ? redisProperties.getSubConnectionMinIdleSize() : 64)
                .setRetryAttempts(redisProperties.getRetryAttempts() != null ? redisProperties.getRetryAttempts() : 3)
                .setTimeout(redisProperties.getTimeout() != null ? redisProperties.getTimeout() : 99999)
                .setRetryInterval(redisProperties.getRetryInterval() != null ? redisProperties.getRetryInterval() : 1500)
                .setIdleConnectionTimeout(redisProperties.getIdleConnectionTimeout() != null ? redisProperties.getIdleConnectionTimeout() : 10000);
        return config;
    }
}
