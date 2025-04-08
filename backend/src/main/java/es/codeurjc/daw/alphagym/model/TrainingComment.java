package es.codeurjc.daw.alphagym.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class TrainingComment{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;

    private String name;
    private boolean isNotified = false;
    
    @ManyToOne
    private Training training;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected TrainingComment() {
    } 

    public TrainingComment(String description, String name) {
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

    public Training getTraining() {
        return training;
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

    public void setTraining(Training training) {
        this.training = training;
    }

    public void setUser(User user) {this.user = user;}
}