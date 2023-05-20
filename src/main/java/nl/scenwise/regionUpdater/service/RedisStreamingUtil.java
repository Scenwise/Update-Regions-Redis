package nl.scenwise.regionUpdater.service;

import lombok.extern.slf4j.Slf4j;
import nl.scenwise.regionUpdater.config.redis.RedisProperties;
import nl.scenwise.regionUpdater.config.redis.RedissonClientConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;


@Slf4j
public class RedisStreamingUtil {

    private static RedisStreamingUtil instance;
    private RedissonClient redisClient;
    private RedisStreamingUtil() {
        initiate();
    }

    public static RedisStreamingUtil getInstance() {
        if(instance==null) instance = new RedisStreamingUtil();
        return instance;
    }

    private void initiate() {
        this.redisClient = buildRedissonClient();
    }

    public RedisProperties redisProperties() {
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setHost("db-redis-ams3-15477-do-user-8143621-0.b.db.ondigitalocean.com");
        redisProperties.setPort(25061);
        redisProperties.setUsername("default");
        redisProperties.setPassword("AVNS_MC1G6de1lGRENdC");
        redisProperties.setSsl(true);
        return redisProperties;
    }

    private RedissonClient buildRedissonClient() {
        return Redisson.create(RedissonClientConfig.createConfig(redisProperties()));
    }

    public RedissonClient getRedisClient() {return this.redisClient;}
}
