package es.codeurjc.daw.alphagym.model;

import java.util.List;
import org.springframework.web.context.annotation.SessionScope;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Blob;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
@SessionScope
public class User {

    @Id ()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name; 

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    private String encodedPassword;

    @Lob
	@JsonIgnore
	private Blob imgUser;

    @JsonIgnore
	private boolean image;

    @JsonIgnore
    private String imgUserPath;

    @ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;

    @OneToMany(cascade=CascadeType.ALL)
    private List<TrainingComment> trainingComments;

    @OneToMany(cascade=CascadeType.ALL)
    private List<NutritionComment> nutritionComments;

    @ManyToMany
    private List<Training> trainings;

    @ManyToMany
    private List<Nutrition> nutritions;
    
    public User(String name, String email, String encodedPassword, String... roles) {
        this.name = name; 
        this.email = email; 
        this.encodedPassword = encodedPassword;
        this.roles = List.of(roles);
    }

    public User() {}

    public Boolean isRole(String rol) {
		return this.roles.contains(rol);
	}

    public void addRole(String role) {
		this.roles.add(role);
	}

    public boolean isImage() {
		return image;
	}

    // Getters

    public List<Training> getTrainings() {
        return trainings;
    }

    public List<Nutrition> getNutritions() {
        return nutritions;
    }
    
    public List<TrainingComment> getTrainingComments() {
        return trainingComments;
    }

    public List<NutritionComment> getNutritionComments() {
        return nutritionComments;
    }

    public Long getId() {
        return id;}

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getEncodedPassword() {
        return encodedPassword;
      }

    public List<String> getRoles() {
		return roles;
	}

    public Blob getImgUser() {
        return imgUser;
    }
      
    public String getImgUserPath() {
        return imgUserPath;
    }

    // Setters

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    public void setNutritions(List<Nutrition> nutritions) {
        this.nutritions = nutritions;
    }

    public void setTrainingComments(List<TrainingComment> trainingComments) {
        this.trainingComments = trainingComments;
    }

    public void setNutritionComments(List<NutritionComment> nutritionComments) {
        this.nutritionComments = nutritionComments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setEncodedPassword(String password) {
      this.encodedPassword = password;
    }

    public void setRoles(List<String> roles) {
		this.roles = roles;
	}

    public void setImgUser(Blob imgUser) {
        this.imgUser = imgUser;
    }
    
    public void setImage(boolean image) {
		this.image = image;
	}

    public void setImgUserPath(String imgUserPath) {
        this.imgUserPath = imgUserPath;
    }
    
} 