package es.codeurjc.daw.alphagym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    private int duration;
    private String intensity;
    private String description;
    private boolean image;
    private String goal;
    private String Img; //for rest

    @Lob
    @JsonIgnore
    private Blob imgTraining;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TrainingComment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Training(String name, String intensity,int duration,  String goal, String description) {
        this.name = name;
        this.intensity = intensity;
        this.duration = duration;
        this.goal = goal;
        this.description = description;
    }

    public Training() {
    }

    // Getters

    public boolean isImage() {
        return image;
    }

    public List<TrainingComment> getComments() {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        return comments;
    }

    public String getImg() {return Img;}

    public Blob getImgTraining() { return imgTraining;}
    public long getId() { return id;}
    public int getDuration() { return duration;}
    public String getName() { return name;}
    public String getIntensity() { return intensity;}
    public String getDescription() { return description;}
    public String getGoal() { return goal;}
    public User getUser() { return user; }

    // Setters

    public void setTrainingComments(List<TrainingComment> comments) {this.comments = comments;}
    public void setId(long id) {this.id = id;}
    public void setDuration(int duration) { this.duration = duration;}
    public void setName(String name) { this.name = name; }
    public void setIntensity(String intensity) { this.intensity = intensity;}
    public void setDescription(String description) {this.description = description;}
    public void setGoal(String goal) { this.goal = goal;}
    public void setUser(User user) {this.user = user;}
    public void setImgTraining(Blob image) {this.imgTraining = image;}
    public void setImage(boolean image) {this.image = image;}

    public void setImg(String img) {Img = img;}
    
}
