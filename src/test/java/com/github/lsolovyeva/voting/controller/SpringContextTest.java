package com.github.lsolovyeva.voting.controller;

import com.github.lsolovyeva.voting.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SpringContextTest {

    @MockBean
    private AppConfig appConfig; // not to run AppConfig twice

    @MockBean
    private CacheManager cacheManager;

    @Test
    public void contextLoads() {
    }
}
