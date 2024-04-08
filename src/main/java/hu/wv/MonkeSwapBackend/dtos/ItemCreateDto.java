package hu.wv.MonkeSwapBackend.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemCreateDto {
    private String title;
    private byte[] itemPicture;
    private String description;
    private String category;
    private Integer priceTier;
}
