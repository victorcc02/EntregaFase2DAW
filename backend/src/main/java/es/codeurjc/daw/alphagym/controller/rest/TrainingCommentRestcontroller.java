package es.codeurjc.daw.alphagym.controller.rest;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.codeurjc.daw.alphagym.dto.TrainingCommentDTO;
import es.codeurjc.daw.alphagym.service.TrainingCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/trainingComments")
public class TrainingCommentRestcontroller {

    @Autowired
    TrainingCommentService trainingCommentService;

    @Operation(summary = "Get all training comments",description="Get all training comments")
    @ApiResponses(value={
        @ApiResponse(responseCode="200",description="Training comments found"),
        @ApiResponse(responseCode="400",description="Bad request"),
        @ApiResponse(responseCode="404",description="Training comments not found")
    })
    @GetMapping("/all")
    public Collection<TrainingCommentDTO> getAllTrainingComments() {
        return trainingCommentService.getAllTrainingCommentsDTO();
    }

    @Operation(summary = "Get training comments by training id",description="Get training comments by training id")
    @ApiResponses(value={
        @ApiResponse(responseCode="200",description="Training comments found"),
        @ApiResponse(responseCode="400",description="Bad request"),
        @ApiResponse(responseCode="404",description="Training comments not found")
    })
    @GetMapping("/{trainingId}/")
    public Collection<TrainingCommentDTO> getTrainingCommentsByTrainingId(@PathVariable Long trainingId) {
        return trainingCommentService.getTrainingCommentsByIdDTO(trainingId);
    }

    @Operation(summary = "Get training comment by id",description="Get training comment by id")
    @ApiResponses(value={
        @ApiResponse(responseCode="200",description="Training comment found"),
        @ApiResponse(responseCode="400",description="Bad request"),
        @ApiResponse(responseCode="404",description="Training comment not found")
    })
    @GetMapping("/comment/{id}/")
    public TrainingCommentDTO getTrainingCommentById(@PathVariable Long id) {
        return trainingCommentService.getSingleTrainingCommentByIdDTO(id);
    }
    
    @Operation(summary = "Get paginated training comments",description="Get paginated training comments")
    @ApiResponses(value={
        @ApiResponse(responseCode="200",description="Training comments found"),
        @ApiResponse(responseCode="400",description="Bad request"),
        @ApiResponse(responseCode="404",description="Training comments not found")
    })
    @GetMapping("/")
    public ResponseEntity<List<TrainingCommentDTO>> getPaginatedTrainingComments(
            @RequestParam Long trainingId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {

        List<TrainingCommentDTO> comments = trainingCommentService.getPaginatedCommentsDTO(trainingId, page, limit);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Create a new training comment",description="Create a new training comment")
    @ApiResponses(value={
        @ApiResponse(responseCode="201",description="Training comment created"),
        @ApiResponse(responseCode="400",description="Bad request"),
        @ApiResponse(responseCode="403",description="Forbidden-Access denied"),
        @ApiResponse(responseCode="404",description="Training comment not created")
    })
    @PostMapping("/")
    public ResponseEntity<TrainingCommentDTO> createTrainingComment(
            @RequestBody TrainingCommentDTO trainingCommentDTO) throws SQLException, IOException {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication != null) {
            trainingCommentDTO = trainingCommentService.createTrainingCommentDTO(trainingCommentDTO, trainingCommentService.getAuthenticationUser());
        }else{
            trainingCommentDTO = trainingCommentService.createTrainingCommentDTO(trainingCommentDTO, null);
        }
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(trainingCommentDTO.id()).toUri();

        return ResponseEntity.created(location).body(trainingCommentDTO);

    }

    @Operation(summary = "Update a training comment",description="Update a training comment")
    @ApiResponses(value={
        @ApiResponse(responseCode="200",description="Training comment updated"),
        @ApiResponse(responseCode="400",description="Bad request"),
        @ApiResponse(responseCode="403",description="Forbidden-Access denied"),
        @ApiResponse(responseCode="404",description="Training comment not updated")
    })
    @PutMapping("/")
    public TrainingCommentDTO updateTrainingComment(
            @RequestParam Long id,
            @RequestBody TrainingCommentDTO updatedCommentDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (trainingCommentService.isOwnerComment(id, authentication) || authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            return trainingCommentService.replaceTrainingCommentDTO(id, updatedCommentDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para editar este entrenamiento");
        }

    }

    @Operation(summary = "Delete a training comment",description="Delete a training comment")
    @ApiResponses(value={
        @ApiResponse(responseCode="200",description="Training comment deleted"),
        @ApiResponse(responseCode="400",description="Bad request"),
        @ApiResponse(responseCode="403",description="Forbidden-Access denied"),
        @ApiResponse(responseCode="404",description="Training comment not deleted")
    })
    @DeleteMapping("/")
    public TrainingCommentDTO deleteTrainingComment(@RequestParam Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication!=null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            return trainingCommentService.deleteCommentbyIdDTO(id);
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No tienes permisos para eliminar este comentario de entrenamiento");
        }
    }

    @Operation(summary = "Report training comment",description="Report training comment")
    @ApiResponses(value={
        @ApiResponse(responseCode="200",description="Training comment reported"),
        @ApiResponse(responseCode="400",description="Bad request"),
        @ApiResponse(responseCode="403",description="Forbidden-Access denied"),
        @ApiResponse(responseCode="404",description="Training comment not reported")
    })
    @PutMapping("/report")
    public ResponseEntity<TrainingCommentDTO> reportComment(@RequestParam Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication!=null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"))) {
            TrainingCommentDTO updatedComment = trainingCommentService.reportTrainingComment(commentId);
            return ResponseEntity.ok(updatedComment);
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No tienes permisos para reportar este comentario de entrenamiento");
        }
        
    }

    @Operation(summary = "Unreport training comment",description="Unreport training comment")
    @ApiResponses(value={
        @ApiResponse(responseCode="200",description="Training comment unreported"),
        @ApiResponse(responseCode="400",description="Bad request"),
        @ApiResponse(responseCode="403",description="Forbidden-Access denied"),
        @ApiResponse(responseCode="404",description="Training comment not unreported")
    })
    @PutMapping("/valid")
    public ResponseEntity<TrainingCommentDTO> unreportComment(@RequestParam Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication!=null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            TrainingCommentDTO updatedComment = trainingCommentService.unreportTrainingComment(commentId);
            return ResponseEntity.ok(updatedComment);
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No tienes permisos para validar este comentario de entrenamiento");
        }
    }

}
