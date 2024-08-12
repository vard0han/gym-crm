package com.example.controllerTest;

import com.example.controller.TraineeController;
import com.example.controller.TrainerController;
import com.example.dto.*;
import com.example.model.AppUser;
import com.example.model.Trainee;
import com.example.model.Trainer;
import com.example.model.TrainingType;
import com.example.service.TraineeService;
import com.example.service.TrainerService;
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
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TrainerController.class)
public class TrainerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainerService trainerService;

    @InjectMocks
    private TrainerController trainerController;

    @Test
    void testRegisterTrainer() throws Exception {
        TrainerRegistrationDto trainerRegistrationDto = new TrainerRegistrationDto("test", "", null);
        ObjectMapper objectMapper = new ObjectMapper();
        String trainerDtoJson = objectMapper.writeValueAsString(trainerRegistrationDto);

        Trainer trainer = new Trainer();
        trainer.setUsername("test");
        trainer.setPassword("password");
        doNothing().when(trainerService).createTrainer(any(Trainer.class));

        mockMvc.perform(post("/api/trainer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(trainerDtoJson))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTrainerProfile() throws Exception {
        Trainer trainer = new Trainer();
        trainer.setFirstName("John");
        trainer.setLastName("Doe");
        when(trainerService.getTrainer("test")).thenReturn(trainer);

        mockMvc.perform(get("/api/trainer/profile")
                        .param("username", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void testUpdateTrainerProfile() throws Exception {
        Trainer trainer = new Trainer();
        trainer.setUsername("test");
        TrainingType trainingType = new TrainingType();
        when(trainerService.getTrainer("test")).thenReturn(trainer);
        mockMvc.perform(put("/api/trainer/profile/update")
                        .param("username", "test")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("isActive", "true")
                        .param("trainingTypeName", "Tp"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void testGetNotAssignedTrainers() throws Exception {
        Trainer trainer = new Trainer();
        AppUser user = new AppUser();
        user.setUsername("test");
        user.setFirstName("John");
        user.setLastName("Doe");
        trainer.setUser(user);
        List<Trainer> trainerDtoList = Collections.singletonList(trainer);
        when(trainerService.getAvailableTrainersForTrainee("test")).thenReturn(trainerDtoList);

        mockMvc.perform(get("/api/trainer/not-assigned")
                        .param("username", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("test"))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));
    }

    @Test
    void testActivateTrainer() throws Exception {
        doNothing().when(trainerService).activateDeactivateTrainer("test", true);

        mockMvc.perform(patch("/api/trainer/activate")
                        .param("username", "test")
                        .param("isActive", "true"))
                .andExpect(status().isOk())
                .andExpect(content().string("200 OK"));
    }
}
