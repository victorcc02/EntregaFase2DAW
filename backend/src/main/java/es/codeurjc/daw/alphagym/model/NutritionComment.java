package es.codeurjc.daw.alphagym.model;

import jakarta.persistence.*;

@Entity
public class NutritionComment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private String name;
    private boolean isNotified = false;

    @ManyToOne
    private Nutrition nutrition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    protected NutritionComment() {
    }

    public NutritionComment(String description, String name) {
        this.description = description;     
        this.name = name;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsNotified() {
        return isNotified;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public User getUser() {return user;}

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name){
        this.name = name;
    } 

    public void setIsNotified(boolean isNotified) {
        this.isNotified = isNotified;
    }
    
    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    public void setUser(User user) {this.user = user;}
}
