package user.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import infra.MockMvcTest;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Base64Utils;
import user.Entity.Address;
import user.Entity.User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private String token = "Bearer ";

    @BeforeEach
    public void setUp() throws Exception {

        mockMvc.perform(post("/users/signup")
                .content(getCorrectSignUpContent("jessicahola@gmail.com"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.msg").value("성공했습니다"));

        MvcResult result = mockMvc.perform(post("/oauth/token")
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("auth_id:auth_secret".getBytes()))
                .param("grant_type", "password")
                .param("client_id", "auth_id")
                .param("scope", "read")
                .param("username", "jessicahola@gmail.com")
                .param("password", "0000")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andReturn();

        String resultString = result.getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        token += jsonParser.parseMap(resultString).get("access_token").toString();
}

    public String getCorrectSignUpContent(String email) {
        return "{\n" +
                "  \"email\" : \"" + email + "\",\n" +
                "  \"name\" : \"jonghyun\",\n" +
                "  \"password\" : \"0000\",\n" +
                "  \"phoneNumber\" : \"01071484933\",\n" +
                "  \"userType\" : 0,\n" +
                "  \"address1\" : \"서울시 광진구 아차산로 375\",\n" +
                "  \"address2\" : \"1111호\",\n" +
                "  \"zip\" : \"0000\",\n" +
                "  \"genres\" : [\"hiphop\"],\n" +
                "  \"locales\" : [],\n" +
                "  \"songCreatedNotification\" : true,\n" +
                "  \"albumCreatedNotification\" : true\n" +
                "}";
    }

    @DisplayName("회원 가입 확인 - 입력값 오류")
    @Test
    void checkSignUp_with_wrong_input() throws Exception {
        mockMvc.perform(post("/users/signup")
                .content("{\n" +
                        "  \"email\" : \"hi\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.msg").value("입력값들을 확인하세요"));
    }

    @DisplayName("회원 가입 확인 - 입력값 정상")
    @Test
    void checkSignUp_with_valid_input() throws Exception {

        mockMvc.perform(post("/users/signup")
                .content(getCorrectSignUpContent("jongjong1994@gmail.com"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.msg").value("성공했습니다"));
    }

    @DisplayName("유저 상세 조회")
    @Test
    void test3() throws Exception {

        mockMvc.perform(get("/users/1")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.msg").value("성공했습니다"))
                .andExpect(jsonPath("$.data").exists());
    }

    @DisplayName("전체 유저 조회(페이징)")
    @Test
    void test4() throws Exception {
        mockMvc.perform(get("/users?page=1")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.msg").value("성공했습니다"))
                .andExpect(jsonPath("$.data").exists());
    }
}