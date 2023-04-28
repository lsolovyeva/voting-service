package org.example.controller;

import org.aspectj.lang.annotation.Before;
import org.example.config.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@Transactional

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(YourController.class)
//@AutoConfigureMockMvc(addFilters = false)
//@ImportAutoConfiguration(classes = {SecurityConfig.class})
public abstract class MockMvcControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public MockMvc mockMvc;

    @MockBean
    private AppConfig appConfig; // not to run AppConfig twice
}
