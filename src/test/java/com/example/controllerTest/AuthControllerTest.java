package com.example.controllerTest;

import com.example.controller.AuthController;
import com.example.service.TraineeService;
import com.example.service.TrainerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TraineeService traineeService;

    @MockBean
    private TrainerService trainerService;

    @Test
    void testLogin_ValidCredentials() throws Exception {
        when(traineeService.validateLogin("user", "password")).thenReturn(true);

        mockMvc.perform(get("/api/login")
                        .param("username", "user")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(content().string("200 OK"));
    }
    @Test
    void testLogin_InvalidCredentials() throws Exception {
        when(traineeService.validateLogin("user", "wrongpassword")).thenReturn(false);
        when(trainerService.validateLogin("user", "wrongpassword")).thenReturn(false);

        mockMvc.perform(get("/api/login")
                        .param("username", "user")
                        .param("password", "wrongpassword"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));
    }

    @Test
    void testChangePassword_Success() throws Exception {
        when(traineeService.changePassword("user", "oldpassword", "newpassword")).thenReturn(true);

        mockMvc.perform(put("/api/login/change")
                        .param("username", "user")
                        .param("oldPassword", "oldpassword")
                        .param("newPassword", "newpassword"))
                .andExpect(status().isOk())
                .andExpect(content().string("200 OK"));
    }

    @Test
    void testChangePassword_InvalidCredentials() throws Exception {
        when(traineeService.changePassword("user", "wrongpassword", "newpassword")).thenReturn(false);
        when(trainerService.changePassword("user", "wrongpassword", "newpassword")).thenReturn(false);

        mockMvc.perform(put("/api/login/change")
                        .param("username", "user")
                        .param("oldPassword", "wrongpassword")
                        .param("newPassword", "newpassword"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials or username"));
    }
}
