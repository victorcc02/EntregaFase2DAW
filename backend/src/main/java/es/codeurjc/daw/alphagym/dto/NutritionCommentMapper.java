package es.codeurjc.daw.alphagym.dto;

import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;

import es.codeurjc.daw.alphagym.model.NutritionComment;

@Mapper(componentModel = "spring", uses = {})
public interface NutritionCommentMapper{
    
    NutritionCommentDTO toDTO(NutritionComment nutritionComment);

    List<NutritionCommentDTO> toDTOs(Collection<NutritionComment> nutritionComments);
    
    NutritionComment toDomain(NutritionCommentDTO nutritionCommentDTO);

}
