package com.github.lsolovyeva.voting.controller;

import com.github.lsolovyeva.voting.config.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCache;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class MockMvcControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public MockMvc mockMvc;

    @MockBean
    private AppConfig appConfig; // not to run AppConfig twice

    @MockBean
    private CacheManager cacheManager;

    @BeforeEach
    void setup() {
        when(cacheManager.getCache(any())).thenReturn(new NoOpCache("testCache"));
    }
}
