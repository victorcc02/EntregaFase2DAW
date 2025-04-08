package es.codeurjc.daw.alphagym.controller.rest;

import java.io.IOException;
import java.net.URI;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.daw.alphagym.dto.UserDTO;
import es.codeurjc.daw.alphagym.model.User;
import es.codeurjc.daw.alphagym.service.NutritionCommentService;
import es.codeurjc.daw.alphagym.service.TrainingCommentService;
import es.codeurjc.daw.alphagym.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

        @Autowired
        private UserService userService;

        @Autowired
        private NutritionCommentService nutritionCommentService;

        @Autowired
        private TrainingCommentService trainingCommentService;

        @Operation(summary = "Get all users")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found all users", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
                        @ApiResponse(responseCode = "401", description = "User not authorized", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Users not found", content = @Content)
        })
        @GetMapping("/all")
        public Collection<UserDTO> getUsers() {

                return userService.getUsers();

        }

        @Operation(summary = "Get authenticated user", description = "Retrieve the currently authenticated user.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Authenticated user found"),
                        @ApiResponse(responseCode = "401", description = "User not authenticated")
        })
        @GetMapping("/me")
        public ResponseEntity<UserDTO> getAuthenticatedUser() {
                return userService.getAuthenticatedUserDto()
                                .map(ResponseEntity::ok)
                                .orElseGet(() -> ResponseEntity.status(401).build());
        }

        @Operation(summary = "Get a user by its id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found the user", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
                        @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
                        @ApiResponse(responseCode = "401", description = "User not authorized", content = @Content),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
        })
        @GetMapping("/{id}")
        public UserDTO getUser(@Parameter(description = "User id", required = true) @PathVariable Long id) {

                Optional<User> user = userService.findById(id);

                if (user.isEmpty()) {
                        throw new NoSuchElementException();
                }

                return userService.getUser(user.get().getName());

        }

        @Operation(summary = "Gets the image of a user by its id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found the user image", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
                        @ApiResponse(responseCode = "401", description = "User not authorized", content = @Content),
                        @ApiResponse(responseCode = "404", description = "User not found, user image not found or doesn't have permission to access it", content = @Content),
        })
        @GetMapping("/{id}/image")
        public ResponseEntity<Object> getUserImage(@PathVariable long id) throws  SQLException {

                Resource userImage = userService.getUserImage(id);

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                UserDTO authenticatedUser = userService.getAuthenticatedUserDto()
                        .orElseThrow(() -> new NoSuchElementException("User not authenticated"));

                if (authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")) || (authenticatedUser.id().equals(id)))  {
                        return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                                .body(userImage);
                }

                throw new AccessDeniedException("You are not allowed to edit this user.");
        }

        @Operation(summary = "Registers a new user")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "User registered correctly", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Bad request, maybe one of the user attributes is missing or the type is not valid", content = @Content),
                        @ApiResponse(responseCode = "409", description = "User already exists", content = @Content)
        })
        @PostMapping("/new")
        public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {

                userDTO = userService.createUser(userDTO);

                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(userDTO.id()).toUri();

                return ResponseEntity.created(location).body(userDTO);

        }

        @Operation(summary = "Deletes a user by its id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User deleted correctly", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "User not authorized", content = @Content),
                        @ApiResponse(responseCode = "403", description = "User not authorized", content = @Content),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

                try {
                        userService.deleteUser(id);
                        return ResponseEntity.noContent().build();
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.notFound().build();
                }
        }

        @Operation(summary = "Registers the image of a user")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Image created correctly", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "User not authorized", content = @Content),
                        @ApiResponse(responseCode = "403", description = "User not authorized", content = @Content),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
        })
        @PostMapping("{id}/image")
        public ResponseEntity<Object> createUserImage(@PathVariable long id, @RequestParam MultipartFile imageFile)
                        throws IOException {

                URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

                userService.createUserImage(id, location, imageFile.getInputStream(), imageFile.getSize());

                return ResponseEntity.created(location).build();
        }

        @Operation(summary = "Update a user by its id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User updated correctly", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
                        @ApiResponse(responseCode = "400", description = "User not updated", content = @Content),
                        @ApiResponse(responseCode = "403", description = "User not authorized", content = @Content)
        })
        @PutMapping("/{id}")
        public UserDTO replaceUser(@RequestBody UserDTO userDTO, @PathVariable Long id) throws SQLException {

                UserDTO authenticatedUser = userService.getAuthenticatedUserDto()
                                .orElseThrow(() -> new NoSuchElementException("User not authenticated"));

                // if is admin, can edit any user
                if (authenticatedUser.roles().contains("ROLE_ADMIN")) {
                        return userService.updateUser(id, userDTO);
                }

                // if is user, can edit only his own user
                if (authenticatedUser.id().equals(id)) {
                        return userService.updateUser(id, userDTO);
                }

                // If not achieve any condition, return error 403 (Forbidden)
                throw new AccessDeniedException("You are not allowed to edit this user.");
        }

        @Operation(summary = "Updates the image of a user")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Image created correctly", content = @Content),
                        @ApiResponse(responseCode = "204", description = "Image updated correctly", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
                        @ApiResponse(responseCode = "401", description = "User not authorized", content = @Content),
                        @ApiResponse(responseCode = "403", description = "User not authorized", content = @Content),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
        })
        @PutMapping("/{id}/image")
        public ResponseEntity<Object> replaceUserImage(@PathVariable long id, @RequestParam MultipartFile imageFile)
                        throws IOException {

                // An user, can edit only his own user image
                Optional<UserDTO> authenticatedUserDto = userService.getAuthenticatedUserDto();
                if (authenticatedUserDto.isPresent() && !authenticatedUserDto.get().id().equals(id) && (authenticatedUserDto.get().roles().contains("ROLE_ADMIN"))) {
                        throw new AccessDeniedException("You are not allowed to edit this user.");
                }else{

                userService.replaceUserImage(id, imageFile.getInputStream(), imageFile.getSize());

                return ResponseEntity.noContent().build();
                }
        }

        @Operation(summary = "Delete user image")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "204", description = "User image deleted", content = @Content),
                @ApiResponse(responseCode = "404", description = "User image not found", content = @Content),
                @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
        })
        @DeleteMapping("/{id}/image")
        public ResponseEntity<Object> deletePostImage(@PathVariable long id) throws IOException, SQLException {

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (authentication!=null && authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                        userService.deleteUserImage(id);
                        return ResponseEntity.noContent().build();
                } else {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                                "No tienes permisos para editar este entrenamiento");
                }

        }
        @GetMapping("/reportedComments")
        public ResponseEntity<List<String>> getReportedComments() {
                List<String> reportedComments = new ArrayList<>();

                Long[] reportsArray1 = trainingCommentService.getReportAmmmounts();
                Long[] reportsArray2 = nutritionCommentService.getReportAmmmounts();

                if (reportsArray1.length == 0 && reportsArray2.length == 0) {
                        return ResponseEntity.noContent().build(); // Return 204 No Content if there are no reported
                                                                   // comments
                } else {
                        reportedComments.add(
                                        "Total comments: " + (reportsArray1[0] + reportsArray2[0] + reportsArray1[1]
                                                        + reportsArray2[1]));
                        reportedComments.add("Total reported comments: " + (reportsArray1[0] + reportsArray2[0]));
                        reportedComments.add("Reported training comments: " + reportsArray1[0]);
                        reportedComments.add("Reported nutrition comments: " + reportsArray2[0]);
                        reportedComments.add("Total valid comments: " + (reportsArray1[1] + reportsArray2[1]));
                        reportedComments.add("Valid training comments: " + reportsArray1[1]);
                        reportedComments.add("Valid nutrition comments: " + reportsArray2[1]);
                }

                return ResponseEntity.ok(reportedComments); // Return 200 OK with the list of comments
        }

}
