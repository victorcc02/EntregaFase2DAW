package es.codeurjc.daw.alphagym.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.daw.alphagym.model.TrainingComment;

public interface TrainingCommentRepository extends JpaRepository<TrainingComment, Long> {
    List<TrainingComment> findByisNotified(boolean isNotified);
    List<TrainingComment> findByTrainingId(Long trainingId);
    long countByIsNotified(boolean isNotified);
    List<TrainingComment> findByIsNotified(boolean b);
    Page<TrainingComment> findByTrainingId(Long trainingId, Pageable pageable);
    
}