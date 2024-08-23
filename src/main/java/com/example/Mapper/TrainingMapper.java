package com.example.Mapper;

import com.example.dto.TrainingDto;
import com.example.dto.TrainingRequestDto;
import com.example.model.Trainee;
import com.example.model.Trainer;
import com.example.model.Training;
import com.example.model.TrainingType;

import static com.example.Mapper.trainerMapper.trainerToDto;

public class TrainingMapper {
    public static TrainingDto trainingToDto(Training training){
        TrainingDto trainingDto = new TrainingDto();
        trainingDto.setTrainingName(training.getTrainingName());
        trainingDto.setTrainingDate(training.getTrainingDate());
        trainingDto.setTrainingDuration(training.getTrainingDuration());
        trainingDto.setTrainingType(training.getTrainingType());
        trainingDto.setTrainer(trainerToDto(training.getTrainer()));
        return trainingDto;
    }

    public static Training DtoToTraining(TrainingRequestDto dto, Trainer trainer, Trainee trainee){
        Training training = new Training();
        training.setTrainingName(dto.getTrainingName());
        training.setTrainingDate(dto.getTrainingDate());
        training.setTrainingDuration(dto.getTrainingDuration());
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        return training;
    }
}
