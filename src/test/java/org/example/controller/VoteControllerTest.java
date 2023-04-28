package org.example.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.TestData.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends MockMvcControllerTest {

    @Test
    @WithUserDetails(value = USER_MAIL)
    void vote() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/user/vote/1"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("\"id\":1")))
                .andExpect(content().string(containsString("voteDate")))
                .andReturn();
        mvcResult.getResponse();
    }

    /*@Test
    @WithUserDetails(value = USER_MAIL)
    void voteAuthorised() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/user/vote/1"))
                .andDo(print())
                .andExpect(status().isCreated())
                //.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                //.andExpect(content().string(containsString("\"id\":3")))
                //.andExpect(content().string(containsString("voteDate")))
                .andReturn();
        mvcResult.getResponse();
    }*/

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getForbidden() {
        assertThatThrownBy(() -> mockMvc.perform(MockMvcRequestBuilders.post("/api/user/vote/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(restaurantRequest))))
                .hasCauseInstanceOf(AccessDeniedException.class).hasMessageContaining("Access Denied");
    }
}
