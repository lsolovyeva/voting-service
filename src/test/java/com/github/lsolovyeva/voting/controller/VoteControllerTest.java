package com.github.lsolovyeva.voting.controller;

import com.github.lsolovyeva.voting.TestData;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends MockMvcControllerTest {

    @Test
    @WithUserDetails(value = TestData.USER_MAIL)
    void vote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/votes?restaurantId=1"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("\"id\":3")))
                .andExpect(content().string(containsString("voteDate")))
                .andReturn();
    }

    @Test
    @WithUserDetails(value = TestData.ADMIN_MAIL)
    void getForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/votes?restaurantId=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestData.asJsonString(TestData.restaurantRequest)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
