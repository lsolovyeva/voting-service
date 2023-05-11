package org.example.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.example.TestData.ADMIN_MAIL;
import static org.example.TestData.USER_MAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RootControllerTest extends MockMvcControllerTest {

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllDishes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/dishes?restaurantId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":1,\"name\":\"Pizza\",\"price\":100.00,\"new\":false},{\"id\":2,\"name\":\"Cake\",\"price\":50.90,\"new\":false}]"))
                .andReturn();
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllRestaurantsWhenAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurants"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":1,\"name\":\"London Paradise\",\"new\":false},{\"id\":2,\"name\":\"Moscow Night\",\"new\":false}]"))
                .andReturn();
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
        // TODO: parameterize user/admin/noAuth
    void getAllRestaurantsWhenUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurants"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":1,\"name\":\"London Paradise\",\"new\":false},{\"id\":2,\"name\":\"Moscow Night\",\"new\":false}]"))
                .andReturn();
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getVotesCount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/votes?restaurantId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("1"))
                .andReturn();
    }
}
