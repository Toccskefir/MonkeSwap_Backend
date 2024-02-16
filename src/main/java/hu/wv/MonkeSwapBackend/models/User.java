package hu.wv.MonkeSwapBackend.models;

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

    @NotNull
    private String role;

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

    //CONSTRUCTORS
    public User() {

    }

    public User(
            Long id,
            String email,
            String username,
            String password,
            Integer tradesCompleted,
            String role,
            String dateOfRegistration,
            String fullName,
            LocalDate dateOfBirth,
            String phoneNumber,
            String profilePicture) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.tradesCompleted = tradesCompleted;
        this.role = role;
        this.dateOfRegistration = dateOfRegistration;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
    }

    public User(
            String email,
            String username,
            String password,
            Integer tradesCompleted,
            String role,
            String dateOfRegistration,
            String fullName,
            LocalDate dateOfBirth,
            String phoneNumber,
            String profilePicture) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.tradesCompleted = tradesCompleted;
        this.role = role;
        this.dateOfRegistration = dateOfRegistration;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
    }
}
