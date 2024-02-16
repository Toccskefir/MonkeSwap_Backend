package hu.wv.MonkeSwapBackend.models;

import hu.wv.MonkeSwapBackend.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String email;

    @Column(unique = true)
    @NotNull
    private String username;

    @NotNull
    private String password;

    @Column(name = "trades_completed")
    @NotNull
    private Integer tradesCompleted;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole role;

    @Column(name = "date_of_registration")
    @NotNull
    private String dateOfRegistration;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "profile_picture")
    private String profilePicture;

    @OneToMany(mappedBy = "userId")
    private List<Item> items;

    @OneToMany(mappedBy = "userId")
    private List<Notification> notifications;
}
