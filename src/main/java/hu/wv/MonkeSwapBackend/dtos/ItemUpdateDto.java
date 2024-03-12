package hu.wv.MonkeSwapBackend.dtos;

import hu.wv.MonkeSwapBackend.enums.ItemCategory;
import lombok.Data;

@Data
public class ItemUpdateDto {
    private String title;
    private String itemPicture;
    private String description;
    private ItemCategory category;
    private String priceTier;
}
