package es.codeurjc.daw.alphagym.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.daw.alphagym.model.NutritionComment;

public interface NutritionCommentRepository extends JpaRepository<NutritionComment, Long> {
    List<NutritionComment> findById(long id);
    long countByIsNotified(boolean isNotified);
    Page<NutritionComment> findByNutritionId(Long nutritionId, Pageable pageable);
    List<NutritionComment> findByNutritionId(Long nutritionId);
    List<NutritionComment> findByIsNotified(boolean b);
    
}
