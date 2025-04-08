package es.codeurjc.daw.alphagym.dto;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.codeurjc.daw.alphagym.model.Nutrition;


@Mapper(componentModel = "spring")
public interface NutritionMapper {

    NutritionDTO toDTO(Nutrition nutrition);

    List<NutritionDTO> toDTOs(Collection<Nutrition> nutritions);

    @Mapping(target = "imgNutrition", ignore = true)
    Nutrition toDomain(NutritionDTO nutritionDTO);

    //@Mapping(target = "books", ignore = true)
    //Shop toDomain(ShopBasicDTO shopDTO);
}
