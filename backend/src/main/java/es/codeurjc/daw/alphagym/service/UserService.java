package es.codeurjc.daw.alphagym.service;

import es.codeurjc.daw.alphagym.dto.UserDTO;
import es.codeurjc.daw.alphagym.model.Training;
import es.codeurjc.daw.alphagym.model.User;
import es.codeurjc.daw.alphagym.repository.UserRepository;
import es.codeurjc.daw.alphagym.security.LoginRequest;

import es.codeurjc.daw.alphagym.security.jwt.JwtTokenProvider;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import es.codeurjc.daw.alphagym.dto.UserMapper;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private UserMapper userMapper;

    private UserDTO toUserDTO(User user) {
        return mapper.toUserDTO(user);
    }

    private User toUser(UserDTO userDTO) {
        return mapper.toUser(userDTO);
    }

    public ResponseEntity<Object> login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()));

            // Save authentication details in security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok("Login successful");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    public User createUser(String name, String email, String pass, String... roles) throws SQLException, IOException {
        User user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setEncodedPassword(passwordEncoder.encode(pass));
        user.setRoles(List.of(roles));

        ClassPathResource imgFileDefault = new ClassPathResource("static/images/emptyImage.png");
        byte[] imageBytes;
        try (InputStream inputStream = imgFileDefault.getInputStream()) {
            imageBytes = inputStream.readAllBytes();
        }
        Blob imageBlob = new SerialBlob(imageBytes);
        user.setImgUser(imageBlob);
        user.setImage(true);

        return user;
    }

    public void updateUserName(Long userId, String newName) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(newName);
        }
    }

    public void updateUserEmail(Long userId, String newEmail) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEmail(newEmail);
        }
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public boolean exist(Long id) {
        return userRepository.existsById(id);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    public UserDTO getUser(String name) {
        return mapper.toUserDTO(userRepository.findByName(name).orElseThrow());
    }

    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.email())) {
            throw new IllegalArgumentException("Email is already in use.");
        }
        User user = toUser(userDTO);
        user.setEncodedPassword(passwordEncoder.encode(userDTO.password()));
        userRepository.save(user);
        return toUserDTO(user);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) throws SQLException {

        User user = userRepository.findById(id).orElseThrow();
        if (userRepository.existsByEmail(userDTO.email())) {
            throw new IllegalArgumentException("Email is already in use.");
        }
        if (userDTO.email() != null && !userDTO.email().isEmpty()) {
            user.setEmail(userDTO.email());
        } else {
            user.setEmail(user.getEmail());
        }
        if (userDTO.password() != null && !userDTO.password().isEmpty()) {
            user.setEncodedPassword(passwordEncoder.encode(userDTO.password()));
        } else {
            user.setEncodedPassword(user.getEncodedPassword());
        }
        if (userDTO.name() != null && !userDTO.name().isEmpty()) {
            user.setName(userDTO.name());
        } else {
            user.setName(user.getName());
        }
        if (user.getImgUser() != null) {
        user.setImgUser(BlobProxy.generateProxy(user.getImgUser().getBinaryStream(), user.getImgUser().length()));
        } else {
            user.setImgUser(null);
        }

        return toUserDTO(userRepository.save(user));
    }

    public void createUserImage(long id, URI location, InputStream inputStream, long size) {

        User user = userRepository.findById(id).orElseThrow();

        user.setImgUserPath(location.toString()); // convert URI to String
        user.setImgUser(BlobProxy.generateProxy(inputStream, size)); // convert InputStream to Blob

        userRepository.save(user);

    }

    public InputStreamResource getUserImage(long id) throws SQLException {

        User user = userRepository.findById(id).orElseThrow();

        if (user.getImgUser() != null) {
            return new InputStreamResource(user.getImgUser().getBinaryStream());
        } else {
            throw new NoSuchElementException();
        }
    }

    public void replaceUserImage(long id, InputStream inputStream, long size) {

        User user = userRepository.findById(id).orElseThrow();
        user.setImgUser(BlobProxy.generateProxy(inputStream, size));
        userRepository.save(user);
    }

    public void deleteUserImage(long id) throws IOException, SQLException {
        User user = userRepository.findById(id).orElseThrow();
        if(user.getImgUser() == null){
            throw new NoSuchElementException();
        }
        ClassPathResource imgFileDefault = new ClassPathResource("static/images/profile-picture-default.jpg");
        byte[] imageBytesDefault = Files.readAllBytes(imgFileDefault.getFile().toPath());
        Blob imageBlobDefault = new SerialBlob(imageBytesDefault);
        user.setImgUser(imageBlobDefault);
        userRepository.save(user);
    }

    public Collection<UserDTO> getUsers() {
        return mapper.toUserDTOs(userRepository.findAll());
    }

    public Optional<UserDTO> getAuthenticatedUserDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userRepository.findByEmail(userDetails.getUsername())
                    .map(userMapper::toUserDTO);
        }

        return Optional.empty();
    }

    public Long getAuthenticatedUserId() {
        return getAuthenticatedUserDto()
                .map(UserDTO::id)
                .orElseThrow(() -> new RuntimeException("User not authenticated"));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

}
