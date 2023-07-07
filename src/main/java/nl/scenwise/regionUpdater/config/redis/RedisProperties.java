package nl.scenwise.regionUpdater.config.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * RedisProperties configuration class that is used to set the following properties:
 *
 * <ul>
 *   <li>host: The Redis server hostname</li>
 *   <li>port: The Redis server port number</li>
 *   <li>password: The Redis server password</li>
 *   <li>username: The Redis server username</li>
 *   <li>sslEnabled: A flag to enable or disable SSL for Redis connections</li>
 *   <li>prefix: The prefix for Redis keys
 *   <li>connectionMinIdleSize: The minimum number of idle connections in the connection pool</li>
 *   <li>connectionPoolSize: The maximum number of connections in the connection pool</li>
 *   <li>subConnectionPoolSize: The maximum number of connections in the subscription connection pool</li>
 *   <li>subConnectionMinIdleSize: The minimum number of idle connections in the subscription connection pool</li>
 *   <li>retryAttempts: The number of attempts to retry a failed Redis command</li>
 *   <li>retryInterval: The interval in milliseconds between retry attempts</li>
 *   <li>timeout: The command timeout in milliseconds</li>
 *   <li>idleConnectionTimeout: The idle connection timeout in milliseconds</li>
 * </ul>
 */
@Data
@Configuration
public class RedisProperties {
    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private Integer port;
    @Value("${redis.username}")
    private String username;
    @Value("${redis.password}")
    private String password;
    @Value("${redis.ssl}")
    private Boolean ssl;
    private String prefix;
    /**
     * Minimum idle connections in the connection pool (default: 24).
     */
    private Integer connectionMinIdleSize;
    /**
     * Maximum connections in the connection pool (default: 64).
     */
    private Integer connectionPoolSize;
    /**
     * Maximum connections in the subscription connection pool (default: 50).
     */
    private Integer subConnectionPoolSize;
    /**
     * Minimum idle connections in the subscription connection pool (default: 1).
     */
    private Integer subConnectionMinIdleSize;
    /**
     * Number of retry attempts when a command fails (default: 3).
     */
    private Integer retryAttempts;
    /**
     * Retry interval in milliseconds between retries (default: 1500).
     */
    private Integer retryInterval;
    /**
     * Redis command timeout in milliseconds (default: 3000).
     */
    private Integer timeout;
    /**
     * Idle connection timeout in milliseconds (default: 10000).
     */
    private Integer idleConnectionTimeout;

}
