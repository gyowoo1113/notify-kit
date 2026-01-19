package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.sse;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class SseSubscribeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void subscribe_endpoint_is_exposed() throws Exception {
        mockMvc.perform(get("/notification/subscribe")
                .param("receiverId","1"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type",
                        Matchers.containsString("text/event-stream")));
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class SseTestApp {
    }
}