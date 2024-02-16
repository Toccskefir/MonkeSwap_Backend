package hu.wv.MonkeSwapBackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "notifications")
@Data
public class Notification {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String message;

    @NotNull
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    //CONSTRUCTORS
    public Notification() {

    }

    public Notification(Long id, String message, String type, User userId) {
        this.id = id;
        this.message = message;
        this.type = type;
        this.userId = userId;
    }

    public Notification(String message, String type, User userId) {
        this.message = message;
        this.type = type;
        this.userId = userId;
    }
}
