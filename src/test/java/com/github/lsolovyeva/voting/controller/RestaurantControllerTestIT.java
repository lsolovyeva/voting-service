package com.github.lsolovyeva.voting.controller;

import com.github.lsolovyeva.voting.TestData;
import com.github.lsolovyeva.voting.config.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class RestaurantControllerTestIT {

    @Autowired
    private WebApplicationContext ctx;

    @MockBean
    private CacheManager cacheManager;

    protected MockMvc mvc;

    @MockBean
    private AppConfig appConfig; // not to run AppConfig twice

    @BeforeEach
    public void setUp() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(ctx)
                .apply(springSecurity())
                .build();
    }

    @Test
    void notGetAllRestaurantsWhenUnauthorized() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/admin/restaurants").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = TestData.USER_MAIL)
    void notCreateDishWhenNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/admin/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestData.asJsonString(TestData.restaurantRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    void notVoteWhenUnauthorized() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/user/votes?restaurantId=1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void notCreateDishWhenUnauthorized() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/admin/dishes?restaurantId=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestData.asJsonString(TestData.dishRequest)))
                .andExpect(status().isUnauthorized());
    }
}
