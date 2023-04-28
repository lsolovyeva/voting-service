package org.example.controller;

import org.example.dto.DishRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.example.TestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DishControllerTest extends MockMvcControllerTest {

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDish() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/menus/add/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dishRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":5,\"name\":\"pizza\",\"price\":50.5,\"new\":false}"))
                .andReturn();

    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDish() throws Exception {
        DishRequest dishRequest = new DishRequest("newPizza", new BigDecimal("100"));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/menus/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dishRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andReturn();
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void removeDish() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/menus/disable/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dishRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andReturn();
    }
}
