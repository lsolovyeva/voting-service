package com.github.lsolovyeva.voting.service;

import com.github.lsolovyeva.voting.config.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheInvalidator {

    @Autowired
    CacheManager cacheManager;

    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict(value = AppConfig.DISHES_CACHE, allEntries = true)
    public void clearCache() {
        log.info("Invalidating dishes cache.");
    }
}
