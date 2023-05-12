package com.github.lsolovyeva.voting.controller;

import com.github.lsolovyeva.voting.TestData;
import com.github.lsolovyeva.voting.dto.RestaurantRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends MockMvcControllerTest {

    @Test
    @WithUserDetails(value = TestData.ADMIN_MAIL)
    void createRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestData.asJsonString(TestData.restaurantRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":3,\"name\":\"testRestaurantName\",\"new\":false}"))
                .andReturn();
    }

    @Test
    @WithUserDetails(value = TestData.USER_MAIL)
    void getForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestData.asJsonString(TestData.restaurantRequest)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = TestData.ADMIN_MAIL)
    void updateRestaurant() throws Exception {
        RestaurantRequest restaurantRequest = new RestaurantRequest("newRestaurantName");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestData.asJsonString(restaurantRequest)))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().string(""))
                .andReturn();
    }
}
