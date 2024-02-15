package hu.wv.MonkeSwapBackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String message;

    @NotNull
    private String type;

    @Column(name = "user_id")
    private Long userId;
}
