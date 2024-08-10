package com.example.Mapper;

import com.example.dto.TraineeDto;
import com.example.dto.TraineeProfileDto;
import com.example.dto.TraineeRegistrationDto;
import com.example.dto.TrainerDto;
import com.example.model.Trainee;
import com.example.model.Trainer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class traineeMapper {
    public static Trainee traineeRegDtoToTrainee(TraineeRegistrationDto traineeDto){
        Trainee trainee = new Trainee();
        trainee.setFirstName(traineeDto.getFirstName());
        trainee.setLastName(traineeDto.getLastName());
        trainee.setDateOfBirth(traineeDto.getDateOfBirth());
        trainee.setAddress(traineeDto.getAddress());

        return trainee;
    }

    public static TraineeProfileDto traineeProfileToDto(Trainee trainee){
        TraineeProfileDto profileDto = new TraineeProfileDto();
        profileDto.setFirstName(trainee.getFirstName());
        profileDto.setLastName(trainee.getLastName());
        profileDto.setDateOfBirth(trainee.getDateOfBirth());
        profileDto.setAddress(trainee.getAddress());
        profileDto.setActive(trainee.isActive());

        Set<Trainer> trainers = trainee.getTrainers();
        List<TrainerDto> trainerDtos = trainers.stream()
                .map(trainerMapper::trainerToDto)
                .collect(Collectors.toList());
        profileDto.setTrainers(trainerDtos);

        return profileDto;
    }

    public static Trainee dtoToTraineeProfile(TraineeProfileDto profileDto) {
        Trainee trainee = new Trainee();
        trainee.setFirstName(profileDto.getFirstName());
        trainee.setLastName(profileDto.getLastName());
        trainee.setDateOfBirth(profileDto.getDateOfBirth());
        trainee.setAddress(profileDto.getAddress());
        trainee.setActive(profileDto.isActive());
        Set<Trainer> trainers = profileDto.getTrainers().stream()
                .map(trainerMapper::dtoToTrainer)
                .collect(Collectors.toSet());

        trainee.setTrainers(trainers);
        return trainee;
    }
}
