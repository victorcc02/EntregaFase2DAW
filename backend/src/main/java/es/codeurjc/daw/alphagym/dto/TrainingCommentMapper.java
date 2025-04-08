package es.codeurjc.daw.alphagym.dto;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;

import es.codeurjc.daw.alphagym.model.TrainingComment;

@Mapper(componentModel = "spring")
public interface TrainingCommentMapper {

    TrainingCommentDTO toDTO(TrainingComment trainingComment);

    List<TrainingCommentDTO> toDTOs(Collection<TrainingComment> trainingComments);
    
    TrainingComment toDomain(TrainingCommentDTO trainingCommentDTO);
    
}
