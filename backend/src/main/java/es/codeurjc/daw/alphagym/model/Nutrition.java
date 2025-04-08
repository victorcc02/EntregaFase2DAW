package es.codeurjc.daw.alphagym.model;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Nutrition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private boolean image;
    private String goal;
    private int calories;

    @Lob
    @JsonIgnore
    private Blob imgNutrition;

    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    private List<NutritionComment> comments;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Nutrition(String name, int calories, String goal, String description) {
        this.name = name;
        this.calories = calories;
        this.goal = goal;
        this.description = description;
    }

    public Nutrition() {
        }
    
        
    // Getters
    public List<NutritionComment> getComments() {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        return comments;
    }

    public Long getId() {
        return this.id;
    }

    public String getGoal() {
        return this.goal;
    }

    public int getCalories() {
        return this.calories;
    }

    public String getName() { 
        return name; }


    public String getDescription() {
        return description;
    }

    public Boolean getImage() {
        return image;
    }

    public User getUser() {
        return this.user;
    }

    //image
    public Blob getImgNutrition() {
        return imgNutrition;
    }


    // Setters
    public void setNutritionComments(List<NutritionComment> comments) {
        this.comments = comments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setName(String name) { 
        this.name = name; 
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(Boolean image) {
        this.image = image;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //image
    public void setImgNutrition(Blob image) {
        this.imgNutrition = image;
    }
    
}
