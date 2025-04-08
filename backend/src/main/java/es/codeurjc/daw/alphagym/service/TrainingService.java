package es.codeurjc.daw.alphagym.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import es.codeurjc.daw.alphagym.dto.TrainingDTO;
import es.codeurjc.daw.alphagym.dto.TrainingMapper;
import es.codeurjc.daw.alphagym.dto.UniqueTrainingDTO;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;

import es.codeurjc.daw.alphagym.model.Training;
import es.codeurjc.daw.alphagym.model.TrainingComment;
import es.codeurjc.daw.alphagym.model.User;
import es.codeurjc.daw.alphagym.repository.TrainingCommentRepository;
import es.codeurjc.daw.alphagym.repository.TrainingRepository;
import es.codeurjc.daw.alphagym.repository.UserRepository;

@Service
public class TrainingService {

    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private TrainingCommentRepository trainingCommentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrainingMapper trainingMapper;

    public Training createTraining(Training training, User user) throws SQLException, IOException {
        Training newTraining = new Training(training.getName(),training.getIntensity(),training.getDuration(),training.getGoal(),training.getDescription());
        newTraining.setUser(user);
        if (training.getImgTraining()!=null){
            newTraining.setImgTraining(training.getImgTraining());
        } else {
            ClassPathResource imgFileDefault = new ClassPathResource("static/images/emptyImage.png");
            byte[] imageBytes;
            try (InputStream inputStream = imgFileDefault.getInputStream()) {
                imageBytes = inputStream.readAllBytes();
            }
            Blob imageBlob = new SerialBlob(imageBytes);
            newTraining.setImgTraining(imageBlob);
        }
        trainingRepository.save(newTraining);
        return newTraining;
    }

    public List<Training> getAllTrainings(){
        List<Training> listTraining = trainingRepository.findAll();
        return listTraining.isEmpty() ? null : listTraining;
    }

    public Training getTraining(Long id){
        Optional<Training> theRoutine = trainingRepository.findById(id);
        if (theRoutine.isPresent()) {
            return theRoutine.get();
        } else {
            return null;
        }
    }

    public Training updateRoutine(Long routineId, Training newTrainingData, User user) {
        Optional<Training> existingTrainingOpt = trainingRepository.findById(routineId);

        if (existingTrainingOpt.isPresent()) {
            Training existingTraining = existingTrainingOpt.get();

            existingTraining.setName(newTrainingData.getName());
            existingTraining.setIntensity(newTrainingData.getIntensity());
            existingTraining.setDuration(newTrainingData.getDuration());
            existingTraining.setGoal(newTrainingData.getGoal());
            existingTraining.setDescription(newTrainingData.getDescription());

            if (newTrainingData.getImgTraining() != null) {
                existingTraining.setImgTraining(newTrainingData.getImgTraining());
                existingTraining.setImage(true);
            }

            if (existingTraining.getUser() != null) {
                existingTraining.setUser(user);
            }

            return trainingRepository.save(existingTraining);
        }

        return null;
    }
    public Optional<Training> findById(Long id) {
        return trainingRepository.findById(id);
    }

    public Training deleteRoutine(Long id){
        Optional<Training> theRoutine = trainingRepository.findById(id);
        if (theRoutine.isPresent()) {
            Training training = theRoutine.get();

            List<User> usersWithTraining = userRepository.findByTrainingsContaining(training);
            for (User user : usersWithTraining) {
                user.getTrainings().remove(training);
                userRepository.save(user); 
            }

            List<TrainingComment> trainingComments = trainingCommentRepository.findByTrainingId(id);

            for (TrainingComment trainingComment : trainingComments) {
                trainingCommentRepository.delete(trainingComment);

            }
            trainingRepository.delete(training);
            return training;
        }
        return null;
    }

    public void subscribeTraining(Long trainingId , User user) {
        Optional<Training> training = trainingRepository.findById(trainingId);
        if (training.isPresent()) {
            user.getTrainings().add(training.get());
            userRepository.save(user);
        }
    }

    public void unsubscribeTraining(Long trainingId, User user) {
        Optional<Training> training = trainingRepository.findById(trainingId);
        if (training.isPresent()) {
            user.getTrainings().remove(training.get());
            userRepository.save(user);
        }
    }

    public List<Training> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return trainingRepository.findAll(); // if there is no search, return all
        }
        return trainingRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public boolean isOwner(Long trainingId, Authentication authentication) {
        return trainingRepository.findWithUserById(trainingId)
                .map(training -> {
                    User user = training.getUser();
                    return user != null && authentication.getName().equals(user.getEmail());
                })
                .orElse(false);
    }

    //Get paginated routines
    public List<Training> getPaginatedRoutines(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return trainingRepository.findAll(pageable).getContent();
    }

    //Part of Rest with DTOs

    public Collection<TrainingDTO> getAllDtoTrainings(){
        return trainingMapper.toDTOs(trainingRepository.findAll());
    }

    public UniqueTrainingDTO getDtoTraining(long trainingId){
        return trainingMapper.toUniqueDTO(trainingRepository.findById(trainingId).get());
    }
    public TrainingDTO toDTO(Training training) {
        return trainingMapper.toDTO(training);
    }

    public Training toDomain(TrainingDTO trainingDTO) {
        return trainingMapper.toDomain(trainingDTO);
    }

    private Collection<TrainingDTO> toDTOs(Collection<Training> trainings) {
        return trainingMapper.toDTOs(trainings);
    }

    public void save (Training training){
        trainingRepository.save(training);
    }

    public TrainingDTO replaceTraining(Long trainingId, TrainingDTO trainingDTO) throws SQLException {
        Training oldTraining = trainingRepository.findById(trainingId).orElseThrow();
        if (trainingRepository.findById(trainingId)!=null) {
            Training training = toDomain(trainingDTO);
            training.setId(trainingId);
            training.setUser(oldTraining.getUser());
            training.setTrainingComments(trainingRepository.findById(trainingId).get().getComments());
            training.setImgTraining(BlobProxy.generateProxy(oldTraining.getImgTraining().getBinaryStream(),oldTraining.getImgTraining().length()));
            trainingRepository.save(training);
            return toDTO(training);
        } else {
            throw new NoSuchElementException();
        }

    }

    public TrainingDTO replaceParcialTraining(Long trainingId, TrainingDTO trainingDTO) throws SQLException {
        Training oldTraining = trainingRepository.findById(trainingId).orElseThrow();
        if (trainingRepository.findById(trainingId)!=null) {
            Training training = toDomain(trainingDTO);
            training.setId(trainingId);
            training.setUser(oldTraining.getUser());
            training.setTrainingComments(trainingRepository.findById(trainingId).get().getComments());
            training.setImgTraining(BlobProxy.generateProxy(oldTraining.getImgTraining().getBinaryStream(),oldTraining.getImgTraining().length()));
            if (training.getName()==null){
                training.setName(oldTraining.getName());
            }
            if (training.getGoal()==null){
                training.setGoal(oldTraining.getGoal());
            }
            if (training.getDescription()==null){
                training.setDescription(oldTraining.getDescription());
            }
            if (training.getIntensity()==null){
                training.setIntensity(oldTraining.getIntensity());
            }
            if (training.getDuration()==0){
                training.setDuration(oldTraining.getDuration());
            }
            trainingRepository.save(training);
            return toDTO(training);
        } else {
            throw new NoSuchElementException();
        }

    }

    public TrainingDTO deleteTraining(Long id){
        Optional<Training> theRoutine = trainingRepository.findById(id);
        if (theRoutine.isPresent()) {
            Training training = theRoutine.get();

            List<User> usersWithTraining = userRepository.findByTrainingsContaining(training);
            for (User user : usersWithTraining) {
                user.getTrainings().remove(training);
                userRepository.save(user);
            }

            List<TrainingComment> trainingComments = trainingCommentRepository.findByTrainingId(id);

            for (TrainingComment trainingComment : trainingComments) {
                trainingCommentRepository.delete(trainingComment);

            }
            trainingRepository.deleteById(id);
            return toDTO(training);
        }
        return null;
    }

    public InputStreamResource getTrainingImage(long trainingId) throws SQLException {
        Training training = trainingRepository.findById(trainingId).orElseThrow();
        if (training.getImgTraining() != null) {
            return new InputStreamResource(training.getImgTraining().getBinaryStream());
        } else {
            throw new NoSuchElementException();  }
    }

    public void createTrainingImage(long trainingId, URI location, InputStream inputStream, long size) {
        Training training = trainingRepository.findById(trainingId).orElseThrow();
        training.setImg(location.toString());
        training.setImgTraining(BlobProxy.generateProxy(inputStream, size));
        trainingRepository.save(training);
    }

    public void replaceTrainingImage(long trainingId, InputStream inputStream, long size) {
        Training training = trainingRepository.findById(trainingId).orElseThrow();
        training.setImgTraining(BlobProxy.generateProxy(inputStream, size));
        trainingRepository.save(training);
    }

    public void deleteTrainingImage(long trainingId) throws IOException, SQLException {
        Training training = trainingRepository.findById(trainingId).orElseThrow();
        if(training.getImgTraining() == null){
            throw new NoSuchElementException();
        }
        ClassPathResource imgFileDefault = new ClassPathResource("static/images/emptyImage.png");
        byte[] imageBytesDefault = Files.readAllBytes(imgFileDefault.getFile().toPath());
        Blob imageBlobDefault = new SerialBlob(imageBytesDefault);
        training.setImgTraining(imageBlobDefault);
        trainingRepository.save(training);
    }

    public List<TrainingDTO> getPaginatedTrainingsDTO(int page, int limit) {
        return trainingRepository
                .findAll(PageRequest.of(page, limit))
                .map(trainingMapper::toDTO)
                .toList();
    }
    public  User getAuthenticationUser (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null ) {
           Optional<User> user = userRepository.findByEmail(authentication.getName());
           if (user.isPresent()){
              return user.get();
           }
        }
        return null;
    }
}



