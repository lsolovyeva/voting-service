package org.example.controller;

import org.example.config.SecurityConfig;
import org.example.dto.RestaurantRequest;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.example.TestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
class RestaurantControllerTest extends MockMvcControllerTest {

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/restaurants/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(restaurantRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":3,\"name\":\"testRestaurantName\",\"new\":false}"))
                .andReturn();
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/restaurants/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(restaurantRequest)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().string("Error: access denied."));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateRestaurant() throws Exception {
        RestaurantRequest restaurantRequest = new RestaurantRequest("newRestaurantName");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/restaurants/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(restaurantRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andReturn();
    }
}
