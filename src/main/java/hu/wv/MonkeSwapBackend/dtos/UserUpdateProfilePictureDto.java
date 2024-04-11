package hu.wv.MonkeSwapBackend.dtos;

import lombok.Data;

@Data
public class UserUpdateProfilePictureDto {
    private byte[] profilePicture;
}
