package com.httptest.spring.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HttpRestController.class)
@ExtendWith(SpringExtension.class)
class HttpRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testGetResponseOfSizeByInput() throws Exception {
        int sizeInBytes = 10;
        mockMvc.perform(get("/size/"+sizeInBytes))
          .andExpect(status().is2xxSuccessful());
    }

}