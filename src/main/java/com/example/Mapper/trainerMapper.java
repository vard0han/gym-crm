package com.example.Mapper;


import com.example.dto.TraineeDto;
import com.example.dto.TrainerDto;
import com.example.dto.TrainerProfileDto;
import com.example.dto.TrainerRegistrationDto;
import com.example.model.AppUser;
import com.example.model.Trainee;
import com.example.model.Trainer;
import com.example.model.TrainingType;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class trainerMapper {
    public static Trainer trainerRegDtoToTrainee(TrainerRegistrationDto trainerDto) {
        Trainer trainer = new Trainer();
        trainer.setFirstName(trainerDto.getFirstName());
        trainer.setLastName(trainerDto.getLastName());
        trainer.setSpecialization(trainerDto.getSpecialization());

        return trainer;
    }

    public static TrainerDto trainerToDto(Trainer trainer) {
        TrainerDto dto = new TrainerDto();
        dto.setUsername(trainer.getUser().getUsername());
        dto.setFirstName(trainer.getUser().getFirstName());
        dto.setLastName(trainer.getUser().getLastName());
        dto.setSpecialization(trainer.getSpecialization());
        return dto;
    }

    public static Trainer dtoToTrainer(TrainerDto dto) {
        Trainer trainer = new Trainer();
        AppUser user = new AppUser();
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        trainer.setUser(user);
        trainer.setSpecialization(dto.getSpecialization());
        return trainer;
    }

    public static TrainerProfileDto trainerProfileToDto(Trainer trainer){
        TrainerProfileDto profileDto = new TrainerProfileDto();
        profileDto.setFirstName(trainer.getFirstName());
        profileDto.setLastName(trainer.getLastName());
        profileDto.setSpecialization(trainer.getSpecialization());
        profileDto.setActive(trainer.isActive());

        Set<Trainee> trainees = trainer.getTrainees();
        List<TraineeDto> traineeDtos = trainees.stream()
                .map(traineeMapper::traineeToDto)
                .collect(Collectors.toList());
        profileDto.setTrainees(traineeDtos);
        return profileDto;
    }
}
