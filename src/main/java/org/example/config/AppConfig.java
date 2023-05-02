package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

@Configuration
@Slf4j
@Profile("!test")
@EnableCaching
public class AppConfig {

    public static final String RESTAURANTS_CACHE = "RestaurantsCache";
    public static final String DISHES_CACHE = "DishesCache";

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        log.info("Start H2 TCP server");
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(RESTAURANTS_CACHE, DISHES_CACHE);
    }
}
