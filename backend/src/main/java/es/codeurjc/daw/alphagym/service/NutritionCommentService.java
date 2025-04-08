package es.codeurjc.daw.alphagym.service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import es.codeurjc.daw.alphagym.dto.NutritionCommentDTO;
import es.codeurjc.daw.alphagym.dto.NutritionCommentMapper;
import es.codeurjc.daw.alphagym.model.Nutrition;
import es.codeurjc.daw.alphagym.model.NutritionComment;
import es.codeurjc.daw.alphagym.model.User;
import es.codeurjc.daw.alphagym.repository.NutritionCommentRepository;
import es.codeurjc.daw.alphagym.repository.UserRepository;

@Service
public class NutritionCommentService {

    @Autowired
    private NutritionCommentRepository nutritionCommentRepository;
    @Autowired
    private NutritionCommentMapper nutritionCommentMapper;
    @Autowired
    private UserRepository userRepository;

    public List<NutritionComment> getAllNutritionComments() {
        List<NutritionComment> listNutritionComment = nutritionCommentRepository.findAll();
        return listNutritionComment.isEmpty() ? null : listNutritionComment;
    }

    public List<NutritionComment> getNutritionComments(Long nutritionId) {
        List<NutritionComment> listNutritionComments = getPaginatedComments(nutritionId, 0, 10);
        return listNutritionComments.isEmpty() ? null : listNutritionComments;
    }

    public void createNutritionComment(NutritionComment nutritionComment, Nutrition nutrition, User user) {
        nutritionComment.setUser(user);
        nutritionComment.setNutrition(nutrition);
        nutritionComment = nutritionCommentRepository.save(nutritionComment);
        nutrition.getComments().add(nutritionComment);
    }

    public void deleteCommentbyId(Nutrition nutrition, Long commentId) {
        NutritionComment comment = nutritionCommentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            nutrition.getComments().remove(comment);
        }
        nutritionCommentRepository.deleteById(commentId);
    }

    public void reportCommentbyId(Long commentId) {
        NutritionComment comment = nutritionCommentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            comment.setIsNotified(true);
            nutritionCommentRepository.save(comment);
        }
    }

    public void unreportCommentbyId(Long commentId) {
        NutritionComment comment = nutritionCommentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            comment.setIsNotified(false);
            nutritionCommentRepository.save(comment);
        }
    }

    public NutritionComment getCommentById(Long commentId) {
        return nutritionCommentRepository.findById(commentId).orElse(null);
    }

    public void updateComment(NutritionComment comment) {
        nutritionCommentRepository.save(comment); // Save updates to the database
    }

    public Long[] getReportAmmmounts() {
        Long reported = nutritionCommentRepository.countByIsNotified(true);
        Long notReported = nutritionCommentRepository.countByIsNotified(false);
        return new Long[] { reported, notReported };
    }

    public List<NutritionComment> getPaginatedComments(Long nutritionId, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<NutritionComment> commentsPage = nutritionCommentRepository.findByNutritionId(nutritionId, pageable);
        return commentsPage.getContent();
    }

    public List<NutritionComment> getReportedComments() {
        List<NutritionComment> listNutritionComments = nutritionCommentRepository.findByIsNotified(true);
        return listNutritionComments.isEmpty() ? null : listNutritionComments;
    }

    @Transactional(readOnly = true)
    public boolean isOwnerComment(Long commentId, Authentication authentication) {
        return nutritionCommentRepository.findById(commentId)
                .map(comment -> {
                    User user = comment.getUser();
                    return user != null && authentication.getName().equals(user.getEmail());
                })
                .orElse(false);
    }

    public String editCommentAdminService(Model model, Long commentId) {
        NutritionComment comment = nutritionCommentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            Nutrition nutrition = comment.getNutrition();
            return "redirect:/nutritionComments/" + nutrition.getId() + "/" + commentId + "/editcomment";
        } else {
            return "redirect:/admin";
        }
    }

    public String deleteCommentAdminService(Model model, Long commentId) {
        NutritionComment comment = nutritionCommentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            Nutrition nutrition = comment.getNutrition();
            deleteCommentbyId(nutrition, commentId);
        }
        return "redirect:/admin";
    }

    public User getAuthorFromComment(Long commentId) {
        NutritionComment comment = nutritionCommentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            return comment.getUser();
        } else {
            return null;
        }
    }

    /*
     * Add the following DTOs methods
     */
    public Collection<NutritionCommentDTO> getAllNutritionCommentsDTO() {
        return nutritionCommentMapper.toDTOs(nutritionCommentRepository.findAll());
    }

    public Collection<NutritionCommentDTO> getNutritionCommentsDTO(Long nutritionId) {
        return nutritionCommentMapper.toDTOs(nutritionCommentRepository.findByNutritionId(nutritionId));
    }

    public NutritionCommentDTO getSingleNutritionCommentByIdDTO(Long nutritionId) {
        return nutritionCommentMapper.toDTO(nutritionCommentRepository.findById(nutritionId).orElse(null));
    }

    public List<NutritionCommentDTO> getPaginatedCommentsDTO(Long nutritionId, int page, int limit) {
        return nutritionCommentRepository
                .findByNutritionId(nutritionId, PageRequest.of(page, limit))
                .map(nutritionCommentMapper::toDTO)
                .toList();
    }

    public NutritionCommentDTO createNutritionCommentDTO(NutritionCommentDTO nutritionCommentDTO, User user) {
        NutritionComment nutritionComment = toDomain(nutritionCommentDTO);
        nutritionComment.setUser(user); // Set the user who created the comment
        nutritionComment = nutritionCommentRepository.save(nutritionComment);
        return toDTO(nutritionComment);
    }

    public NutritionCommentDTO replaceNutritionCommentDTO(Long nutritionId, NutritionCommentDTO updatedCommentDTO) {

        // Verify if the comment exists in the database
        if (nutritionCommentRepository.existsById(nutritionId)) {

            // Convert the updated DTO to the domain entity
            NutritionComment updatedComment = toDomain(updatedCommentDTO);

            // Set the nutrition and user for the updated comment
            updatedComment.setUser(getAuthorFromComment(nutritionId));

            // Assign the same ID to the updated comment
            updatedComment.setId(nutritionId);

            // Save the updated comment in the database
            nutritionCommentRepository.save(updatedComment);

            // Return the updated comment DTO
            return toDTO(updatedComment);

        } else {
            // Throw an exception if the comment is not found
            throw new NoSuchElementException("No se encontró el comentario con id: " + nutritionId);
        }
    }

    public NutritionCommentDTO deleteCommentbyIdDTO(Long commentId) {
        Optional<NutritionComment> nutritionComment = nutritionCommentRepository.findById(commentId);
        nutritionCommentRepository.deleteById(commentId);
        return toDTO(nutritionComment.orElse(null));
    }

    public NutritionCommentDTO reportNutritionComment(Long commentId) {
        // Search a comment in the database
        Optional<NutritionComment> optionalComment = nutritionCommentRepository.findById(commentId);

        if (optionalComment.isPresent()) {
            NutritionComment comment = optionalComment.get();
            comment.setIsNotified(true); // Mark it down as reported
            nutritionCommentRepository.save(comment); // Save changes

            return toDTO(comment); // Return the DTO of the updated comment
        } else {
            throw new NoSuchElementException("No se encontró el comentario con id: " + commentId);
        }
    }

    public NutritionCommentDTO unreportNutritionComment(Long commentId) {
        // Search a comment in the database
        Optional<NutritionComment> optionalComment = nutritionCommentRepository.findById(commentId);

        if (optionalComment.isPresent()) {
            NutritionComment comment = optionalComment.get();
            comment.setIsNotified(false); // Mark it down as reported
            nutritionCommentRepository.save(comment); // Save changes

            return toDTO(comment); // Return the DTO of the updated comment
        } else {
            throw new NoSuchElementException("No se encontró el comentario con id: " + commentId);
        }
    }

    // Send to API
    public NutritionCommentDTO toDTO(NutritionComment nutritionComment) {
        return nutritionCommentMapper.toDTO(nutritionComment);
    }

    // Return a comment List to API
    public Collection<NutritionCommentDTO> toDTOs(Collection<NutritionComment> nutritionComments) {
        return nutritionCommentMapper.toDTOs(nutritionComments);
    }

    // Data which comes from API result converted to the expected structure in the
    // backend
    public NutritionComment toDomain(NutritionCommentDTO nutritionCommentDTO) {
        return nutritionCommentMapper.toDomain(nutritionCommentDTO);
    }

    public User getAuthenticationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Optional<User> user = userRepository.findByEmail(authentication.getName());
            if (user.isPresent()) {
                return user.get();
            }
        }
        return null;
    }

    public NutritionCommentDTO patchUpdateNutritionCommentDTO(Long id, NutritionCommentDTO updateDTO) {
        NutritionComment comment = nutritionCommentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentario no encontrado"));

        if (updateDTO.description() != null)
            comment.setDescription(updateDTO.description());
        if (updateDTO.name() != null)
            comment.setName(updateDTO.name());
        if (updateDTO.isNotified() != null)
            comment.setIsNotified(updateDTO.isNotified());

        return toDTO(nutritionCommentRepository.save(comment));
    }

}
