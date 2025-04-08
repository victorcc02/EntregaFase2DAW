package es.codeurjc.daw.alphagym.controller.rest;

import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.io.IOException;

import es.codeurjc.daw.alphagym.dto.TrainingDTO;
import es.codeurjc.daw.alphagym.dto.UniqueTrainingDTO;
import es.codeurjc.daw.alphagym.model.Training;
import es.codeurjc.daw.alphagym.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/trainings")
public class TrainingRestController {

    @Autowired
    TrainingService trainingService;

    @Operation(summary = "Get all trainings")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found all trainings", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingDTO.class))}),
        @ApiResponse(responseCode = "400", description = "Bad request - Invalid parameters", content = @Content),
        @ApiResponse(responseCode = "401", description = "Unauthorized access - Authentication is required", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden - You don't have permission to access", content = @Content),
        @ApiResponse(responseCode = "404", description = "Trainings not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/")
    public Collection<TrainingDTO> getTrainings() {
        return trainingService.getAllDtoTrainings();
    }

    @Operation(summary = "Get a training by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Training found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UniqueTrainingDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access - Authentication is required", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - You don't have permission to access", content = @Content),
            @ApiResponse(responseCode = "404", description = "Training not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{trainingId}")
    public UniqueTrainingDTO getTrainingByIdd(@PathVariable long trainingId) {
        return trainingService.getDtoTraining(trainingId);
    }

    @Operation(summary = "Create a training")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Training created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access - Authentication is required", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - You don't have permission to access", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<TrainingDTO> createTraining(@RequestBody TrainingDTO trainingDTO)
            throws SQLException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Training training = trainingService.toDomain(trainingDTO);
        if(authentication!=null){
            training = trainingService.createTraining(training, trainingService.getAuthenticationUser());
        }else{
            training = trainingService.createTraining(training, null);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(training.getId()).toUri();

        return ResponseEntity.created(location).body(trainingService.toDTO(training));

    }

    @Operation(summary = "Replace a training")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Training replaced", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access - Authentication is required", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - You don't have permission to access", content = @Content),
            @ApiResponse(responseCode = "404", description = "Training not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{trainingId}")
    public TrainingDTO replacePost(@PathVariable long trainingId, @RequestBody TrainingDTO trainingDTO)
            throws SQLException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (trainingService.isOwner(trainingId, authentication) || authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {

            return trainingService.replaceTraining(trainingId, trainingDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No tienes permisos para editar este entrenamiento");
        }
    }

    @Operation(summary = "Replace parcial training")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Training atributes replaced", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access - Authentication is required", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - You don't have permission to access", content = @Content),
            @ApiResponse(responseCode = "404", description = "Training not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PatchMapping("/{trainingId}")
    public TrainingDTO replaceParcialTraining(@PathVariable long trainingId, @RequestBody TrainingDTO trainingDTO)
            throws SQLException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (trainingService.isOwner(trainingId, authentication) || authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {

            return trainingService.replaceParcialTraining(trainingId, trainingDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No tienes permisos para editar este entrenamiento");
        }
    }

    @Operation(summary = "Delete a training")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Training deleted", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access - Authentication is required", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - You don't have permission to access", content = @Content),
            @ApiResponse(responseCode = "404", description = "Training not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{trainingId}")
    public TrainingDTO deleteTraining(@PathVariable long trainingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication!=null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            return trainingService.deleteTraining(trainingId);
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No tienes permisos para eliminar este entrenamiento");
        }
    }

    @Operation(summary = "Retrieve training image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Training image found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Training image not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{trainingId}/image")
    public ResponseEntity<Object> getTrainingImage(@PathVariable long trainingId) throws SQLException {
        Resource trainingImage = trainingService.getTrainingImage(trainingId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg").body(trainingImage);
    }

    @Operation(summary = "Create training image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Training image created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access - Authentication is required", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/{trainingId}/image")
    public ResponseEntity<Object> createTrainingImage(@PathVariable long trainingId,
            @RequestParam MultipartFile imageFile) throws IOException {
        URI location = fromCurrentRequest().build().toUri();
        trainingService.createTrainingImage(trainingId, location, imageFile.getInputStream(), imageFile.getSize());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Replace training image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Training image replaced", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access - Authentication is required", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{trainingId}/image")
    public ResponseEntity<Object> replaceTrainingImage(@PathVariable long trainingId,
            @RequestParam MultipartFile imageFile) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (trainingService.isOwner(trainingId, authentication) || authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {

            trainingService.replaceTrainingImage(trainingId, imageFile.getInputStream(), imageFile.getSize());
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No tienes permisos para editar este entrenamiento");
        }

    }

    @Operation(summary = "Delete training image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Training image deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Training image not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{trainingId}/image")
    public ResponseEntity<Object> deletePostImage(@PathVariable long trainingId) throws IOException, SQLException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication!=null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
             trainingService.deleteTrainingImage(trainingId);
             return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No tienes permisos para editar este entrenamiento");
        }

    }

    @Operation(summary = "Get paginated trainings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainings retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid pagination parameters", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/paginated")
    public ResponseEntity<List<TrainingDTO>> getPaginatedTrainings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {

        List<TrainingDTO> trainingDTOs = trainingService.getPaginatedTrainingsDTO(page, limit);
        return ResponseEntity.ok(trainingDTOs);
    }
}