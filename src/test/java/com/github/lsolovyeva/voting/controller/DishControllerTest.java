package com.github.lsolovyeva.voting.controller;

import com.github.lsolovyeva.voting.TestData;
import com.github.lsolovyeva.voting.dto.DishRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DishControllerTest extends MockMvcControllerTest {

    @Test
    @WithUserDetails(value = TestData.ADMIN_MAIL)
    void createDish() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/dishes?restaurantId=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestData.asJsonString(TestData.dishRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":5,\"name\":\"pizza\",\"price\":50.5,\"new\":false}"))
                .andReturn();

    }

    @Test
    @WithUserDetails(value = TestData.ADMIN_MAIL)
    void updateDish() throws Exception {
        DishRequest dishRequest = new DishRequest("newPizza", new BigDecimal("100"));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/dishes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestData.asJsonString(dishRequest)))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().string(""))
                .andReturn();
    }
}
