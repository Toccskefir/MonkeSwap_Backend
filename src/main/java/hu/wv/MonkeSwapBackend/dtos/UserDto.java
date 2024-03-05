package hu.wv.MonkeSwapBackend.dtos;

import hu.wv.MonkeSwapBackend.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private Integer tradesCompleted;
    private UserRole role;
    private String dateOfRegistration;
    private String fullName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String profilePicture;
}
