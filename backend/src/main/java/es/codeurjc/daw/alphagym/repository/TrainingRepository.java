package es.codeurjc.daw.alphagym.repository;

import java.util.List;
import java.util.Optional;

import es.codeurjc.daw.alphagym.model.Training;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByName(String name);
    Optional<Training> findById(Long id);
    Page<Training> findById(Long id, Pageable pageable);

    @EntityGraph(attributePaths = "user")
    Optional<Training> findWithUserById(Long id);
    
}
