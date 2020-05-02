package controller;

import infra.MockMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@MockMvcTest
class SongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String token = "Bearer ";

}