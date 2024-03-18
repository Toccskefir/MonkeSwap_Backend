package hu.wv.MonkeSwapBackend.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateDto {
    private String username;
    private String fullName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String profilePicture;
}
