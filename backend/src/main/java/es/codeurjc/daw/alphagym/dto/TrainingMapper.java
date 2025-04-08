package es.codeurjc.daw.alphagym.dto;

import java.util.Collection;
import java.util.List;

import es.codeurjc.daw.alphagym.model.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrainingMapper {

    UniqueTrainingDTO toUniqueDTO(Training training);

    TrainingDTO toDTO(Training training);

    List<TrainingDTO> toDTOs(Collection<Training> trainings);

    @Mapping(target = "imgTraining", ignore = true)
    Training toDomain(TrainingDTO trainingDTO);

}
