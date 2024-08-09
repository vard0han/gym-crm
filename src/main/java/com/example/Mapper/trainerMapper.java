package com.example.Mapper;


import com.example.dto.TrainerRegistrationDto;
import com.example.model.Trainer;
import com.example.model.TrainingType;

public class trainerMapper {
    public static Trainer trainerRegDtoToTrainee(TrainerRegistrationDto trainerDto) {
        Trainer trainer = new Trainer();
        trainer.setFirstName(trainerDto.getFirstName());
        trainer.setLastName(trainerDto.getLastName());
        trainer.setSpecialization(trainerDto.getSpecialization());

        return trainer;
    }
}
