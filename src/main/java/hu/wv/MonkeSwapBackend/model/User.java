package hu.wv.MonkeSwapBackend.model;

import hu.wv.MonkeSwapBackend.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "profile_picture", length = 1000000)
    @Lob
    private byte[] profilePicture;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<Item> items;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<Notification> notifications;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //Overrides the UserDetails getUsername method to return email instead of username
    @Override
    public String getUsername() {
        return this.email;
    }

    //Method to return username because getUsername() method returns the email
    public String getRealUsername() {
        return this.username;
    }
}
