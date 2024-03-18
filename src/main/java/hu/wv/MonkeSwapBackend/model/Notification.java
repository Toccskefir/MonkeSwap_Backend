package hu.wv.MonkeSwapBackend.model;

import hu.wv.MonkeSwapBackend.enums.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String message;

    @Enumerated(EnumType.STRING)
    @NotNull
    private NotificationType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
