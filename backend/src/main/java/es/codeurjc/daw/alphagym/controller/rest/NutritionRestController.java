package es.codeurjc.daw.alphagym.controller.rest;

import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.daw.alphagym.dto.NutritionDTO;
import es.codeurjc.daw.alphagym.model.Nutrition;
import es.codeurjc.daw.alphagym.service.NutritionService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/nutritions")
public class NutritionRestController {

    @Autowired
    private NutritionService nutritionService;

    @Operation(summary = "Get all nutritions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all nutritions", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Nutrition.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid parametes", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access - Authentication is required", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - You don't have permission to access", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nutritions not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })

    @GetMapping("/")
    public Collection <NutritionDTO> getNutritions () {
        
        return nutritionService.getAllNutritionsDTO(); 
    }
    
    @Operation(summary = "Get a nutrition by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nutrition found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = NutritionDTO.class))}),
        @ApiResponse(responseCode = "400", description = "Bad request - Invalid parameters", content = @Content),
        @ApiResponse(responseCode = "401", description = "Unauthorized access - Authentication is required", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden - You don't have permission to access", content = @Content),
        @ApiResponse(responseCode = "404", description = "Nutrition not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })

    @GetMapping("/{id}")
    public NutritionDTO getNutrition(@PathVariable Long id) {

        return nutritionService.getNutritionDTO(id);
    }

    @Operation(summary = "Create a new nutrition")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Nutrition created successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = NutritionDTO.class))}),
        @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data", content = @Content),
        @ApiResponse(responseCode = "401", description = "Unauthorized access - Authentication is required", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden - You don't have permission to access", content = @Content),
        @ApiResponse(responseCode = "409", description = "Conflict - Nutrition already exists", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })

   @PostMapping("/")
   public ResponseEntity<NutritionDTO> createNutrition (@RequestBody NutritionDTO nutritionDTO)
            throws SQLException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Nutrition nutrition = nutritionService.toDomain(nutritionDTO);
        if(authentication!=null){
            nutrition = nutritionService.createNutrition(nutrition, nutritionService.getAuthenticationUser());
        }else{
            nutrition = nutritionService.createNutrition(nutrition, null);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nutrition.getId()).toUri();

        return ResponseEntity.created(location).body(nutritionService.toDTO(nutrition));

    }
    /*public ResponseEntity<NutritionDTO> createNutrition (@RequestBody NutritionDTO nutritionDTO){

            nutritionDTO = nutritionService.createNutritionDTO(nutritionDTO);

            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(nutritionDTO.id()).toUri();

            return ResponseEntity.created(location).body(nutritionDTO);
    }*/

    @Operation(summary = "Edit a nutrition by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nutrition updated successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = NutritionDTO.class))}),
        @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data", content = @Content),
        @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden - You don't have permission to edit", content = @Content),
        @ApiResponse(responseCode = "404", description = "Nutrition not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })

    @PutMapping("/{id}")
    public NutritionDTO editDiet (@PathVariable Long id, @RequestBody NutritionDTO updateNutritionDTO) throws SQLException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (nutritionService.isOwner(id, authentication) || authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {

            return nutritionService.editDietDTO(id, updateNutritionDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No tienes permisos para editar esta dieta");
        }
    }

    @Operation(summary = "Partially update a nutrition by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nutrition partially updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NutritionDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - You don't have permission to edit", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nutrition not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PatchMapping("/{id}")
    public NutritionDTO patchNutrition(@PathVariable Long id, @RequestBody NutritionDTO partialUpdateDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (nutritionService.isOwner(id, authentication) || authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {

            return nutritionService.partialUpdateNutritionDTO(id, partialUpdateDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No tienes permisos para editar esta dieta");
        }
    }

    @Operation(summary = "Delete a nutrition by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nutrition deleted successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = NutritionDTO.class))}),
        @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden - You don't have permission to delete", content = @Content),
        @ApiResponse(responseCode = "404", description = "Nutrition not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })

    @DeleteMapping("/{id}")
    public NutritionDTO deleteNutrition(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
 
        if (authentication!=null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            return nutritionService.deleteDietDTO(id);
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No tienes permisos para eliminar esta dieta");
        }
    }

    @Operation(summary = "Upload an image for a nutrition")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Image uploaded successfully", content = @Content),
        @ApiResponse(responseCode = "400", description = "Bad request - Invalid file format", content = @Content),
        @ApiResponse(responseCode = "404", description = "Nutrition not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })

    @PostMapping("/{id}/image")
    public ResponseEntity<Object> createNutritionImage(@PathVariable long id, @RequestParam MultipartFile imgNutrition) throws IOException {

        URI location = fromCurrentRequest().build().toUri();
        nutritionService.createNutritionImage(id, imgNutrition.getInputStream(), imgNutrition.getSize());

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Retrieve the image of a nutrition")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Image retrieved successfully", content = @Content),
        @ApiResponse(responseCode = "404", description = "Nutrition or image not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })

    @GetMapping("/{id}/image")
    public ResponseEntity<Object> getNutritionImage(@PathVariable long id) throws SQLException, IOException {

        Resource postImage = nutritionService.getNutritionImage(id);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").body(postImage);
    }

    @Operation(summary = "Replace the image of a nutrition")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Image replaced successfully", content = @Content),
        @ApiResponse(responseCode = "400", description = "Bad request - Invalid file format", content = @Content),
        @ApiResponse(responseCode = "404", description = "Nutrition not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })

    @PutMapping("/{id}/image")
    public ResponseEntity<Object> replaceNutritionImage(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (nutritionService.isOwner(id, authentication) || authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {

            nutritionService.replaceNutritionImage(id, imageFile.getInputStream(), imageFile.getSize());

            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No tienes permisos para editar este entrenamiento");
        }
    }

    @Operation(summary = "Delete the image of a nutrition")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Image deleted successfully", content = @Content),
        @ApiResponse(responseCode = "404", description = "Nutrition not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })

    @DeleteMapping("/{id}/image")
    public ResponseEntity<Object> deleteNutritionImage(@PathVariable long id) throws IOException, SQLException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication!=null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            nutritionService.deleteNutritionImage(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No tienes permisos para editar este entrenamiento");
        }

    }

    @Operation(summary = "Get paginated nutritions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nutritions retrieved successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = NutritionDTO.class))}),
        @ApiResponse(responseCode = "400", description = "Bad request - Invalid pagination parameters", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })

    @GetMapping("/paginated")
    public ResponseEntity<List<NutritionDTO>> getPaginatedNutritions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {

        List<NutritionDTO> nutritionDTOs = nutritionService.getPaginatedNutritionsDTO(page, limit);
        return ResponseEntity.ok(nutritionDTOs);
    }


    
}
