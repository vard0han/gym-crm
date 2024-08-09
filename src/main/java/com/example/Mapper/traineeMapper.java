package com.example.Mapper;

import com.example.dto.TraineeDto;
import com.example.dto.TraineeRegistrationDto;
import com.example.model.Trainee;

public class traineeMapper {
    public static Trainee traineeRegDtoToTrainee(TraineeRegistrationDto traineeDto){
        Trainee trainee = new Trainee();
        trainee.setFirstName(traineeDto.getFirstName());
        trainee.setLastName(traineeDto.getLastName());
        trainee.setDateOfBirth(traineeDto.getDateOfBirth());
        trainee.setAddress(traineeDto.getAddress());

        return trainee;
    }
}
