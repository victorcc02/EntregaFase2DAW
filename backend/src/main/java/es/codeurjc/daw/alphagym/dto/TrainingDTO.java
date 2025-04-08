package es.codeurjc.daw.alphagym.dto;

public record TrainingDTO (
        Long id,
        String name,
        String description,
        String goal,
        String intensity,
        int  duration){
}
