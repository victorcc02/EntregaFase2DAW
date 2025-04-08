package es.codeurjc.daw.alphagym.repository;

import java.util.List;

import es.codeurjc.daw.alphagym.model.Training;
import es.codeurjc.daw.alphagym.model.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.daw.alphagym.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @SuppressWarnings("null")
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    boolean existsByEmail(String email);
    List<User> findByTrainingsContaining(Training training);
    List<User> findByNutritionsContaining(Nutrition nutrition);
}
