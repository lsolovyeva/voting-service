package org.example.controller;

import org.example.config.SecurityConfig;
import org.example.dto.RestaurantRequest;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.TestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
class RestaurantControllerTest extends MockMvcControllerTest {

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/restaurants")
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
    void getForbidden() {
        assertThatThrownBy(() -> mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(restaurantRequest)))
                .andDo(print())
                .andExpect(status().isForbidden())).hasCause(new AccessDeniedException("Access Denied"));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateRestaurant() throws Exception {
        RestaurantRequest restaurantRequest = new RestaurantRequest("newRestaurantName");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(restaurantRequest)))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().string(""))
                .andReturn();
    }
}
