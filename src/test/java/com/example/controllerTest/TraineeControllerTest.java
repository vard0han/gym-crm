package com.example.controllerTest;


import com.example.controller.TraineeController;
import com.example.dto.TraineeProfileDto;
import com.example.dto.TraineeRegistrationDto;
import com.example.dto.TrainerDto;
import com.example.dto.TrainingDto;
import com.example.model.Trainee;
import com.example.service.TraineeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TraineeController.class)
public class TraineeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TraineeService traineeService;

    @InjectMocks
    private TraineeController traineeController;

    @Test
    void testRegisterTrainee() throws Exception {
        TraineeRegistrationDto traineeRegistrationDto = new TraineeRegistrationDto("test", "", null, "null");
        ObjectMapper objectMapper = new ObjectMapper();
        String traineeDtoJson = objectMapper.writeValueAsString(traineeRegistrationDto);

        doNothing().when(traineeService).createTrainee(any(Trainee.class));

        mockMvc.perform(post("/api/trainee/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(traineeDtoJson))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTraineeProfile() throws Exception {
        Trainee trainee = new Trainee();
        trainee.setUsername("test");
        trainee.setFirstName("user");
        when(traineeService.getTrainee("test")).thenReturn(trainee);

        mockMvc.perform(get("/api/trainee/profile")
                        .param("username", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("user"));
    }

    @Test
    void testUpdateTraineeProfile() throws Exception {
        Trainee trainee = new Trainee();
        trainee.setUsername("test");

        when(traineeService.getTrainee("test")).thenReturn(trainee);

        mockMvc.perform(put("/api/trainee/profile/update")
                        .param("Username", "test")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("isActive", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void testDeleteTraineeProfile() throws Exception {
        doNothing().when(traineeService).deleteTrainee("test");

        mockMvc.perform(delete("/api/trainee/profile/delete")
                        .param("username", "test"))
                .andExpect(status().isOk())
                .andExpect(content().string("200 ok"));
    }

    @Test
    void testUpdateTraineeTrainers() throws Exception {
        when(traineeService.updateTraineeTrainers(anyString(), anyList())).thenReturn(Collections.emptyList());

        mockMvc.perform(put("/api/trainee/trainers")
                        .param("traineeUsername", "test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"trainer1\", \"trainer2\"]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetTraineeTrainings() throws Exception {
        when(traineeService.getTraineeTrainings(anyString(), any(), any(), any(), any()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/trainee/trainings")
                        .param("username", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testActivateTrainee() throws Exception {
        doNothing().when(traineeService).activateDeactivateTrainee("test", true);

        mockMvc.perform(patch("/api/trainee/activate")
                        .param("username", "test")
                        .param("isActive", "true"))
                .andExpect(status().isOk())
                .andExpect(content().string("200 ok"));
    }
}
