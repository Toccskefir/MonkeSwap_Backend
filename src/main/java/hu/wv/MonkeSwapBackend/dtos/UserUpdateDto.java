package hu.wv.MonkeSwapBackend.dtos;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String username;
    private String fullName;
    private String dateOfBirth;
    private String phoneNumber;
}
