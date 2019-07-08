/**
 * @(#)RedisConfig, 2016/11/7.
 * <p>
 * Copyright 2016 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.zhiliao.wpserver.wpwebbackend.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Copyright 2019 OnlyIssilence, Inc. All rights reserved.
 *
 * @Author: MuYa
 * @Date: 2019/7/9
 * @Time: 0:28
 * @Desc:
 */
@Configuration
public class RedisConfig {

    private static final Log LOG = LogFactory.getLog(RedisConfig.class);

    /**
     * Redis related configuration.
     */
    @Value("${server.redis.host-and-port}")
    public String REDIS_HOST_AND_PORT;

    @Value("${server.redis.password}")
    public String REDIS_PASSWORD;

    /**
     * ms
     */
    @Value("${server.redis.timeout}")
    public Integer REDIS_TIMEOUT;

    /**
     * max connection num which can be borrowed from pool.
     */
    @Value("${server.redis.max-active}")
    public Integer REDIS_MAX_ACTIVE;

    /**
     * max connection num which can keep alive when idle.
     */
    @Value("${server.redis.max-idle}")
    public Integer REDIS_MAX_IDLE;

    /**
     * max wait time when borrowing a connection from pool.
     */
    @Value("${server.redis.max-wait-time}")
    public Integer REDIS_MAX_WAIT_TIME;

    @Bean(name = "shardedJedisPool")
    public ShardedJedisPool shardedJedisPool() {
        LOG.info("Redis hosts: " + REDIS_HOST_AND_PORT);
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        String[] hostAndPorts = REDIS_HOST_AND_PORT.split(",");
        for (String hostAndPort : hostAndPorts) {
            int partition = hostAndPort.indexOf(":");
            JedisShardInfo shardInfo = new JedisShardInfo(hostAndPort
                    .substring(0, partition), Integer.parseInt(hostAndPort
                    .substring(partition + 1)), REDIS_TIMEOUT);
            if(!StringUtils.isEmpty(REDIS_PASSWORD)) {
                shardInfo.setPassword(REDIS_PASSWORD);
            }
            shards.add(shardInfo);
        }
        JedisPoolConfig conf = new JedisPoolConfig();
        conf.setMaxTotal(REDIS_MAX_ACTIVE);
        conf.setMaxIdle(REDIS_MAX_IDLE);
        conf.setMaxWaitMillis(
                REDIS_MAX_WAIT_TIME);
        return new ShardedJedisPool(conf, shards, Pattern.compile(":(.+?):"));
    }
}
