package hu.wv.MonkeSwapBackend.dtos;

import lombok.Data;

@Data
public class NotificationCreateDto {
    private String message;
    private String type;
    private Long userId;
}
