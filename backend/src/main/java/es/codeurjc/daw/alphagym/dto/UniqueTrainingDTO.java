package es.codeurjc.daw.alphagym.dto;

public record UniqueTrainingDTO(
        Long id,
        String name,
        String description,
        String goal,
        String intensity,
        int duration

        /* List <TrainingCommentDTO> trainingComments*/) {
}
