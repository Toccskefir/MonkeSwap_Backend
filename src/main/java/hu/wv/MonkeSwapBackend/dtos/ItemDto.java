package hu.wv.MonkeSwapBackend.dtos;

import hu.wv.MonkeSwapBackend.enums.ItemCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;
    private String title;
    private String itemPicture;
    private String description;
    private Integer views;
    private ItemCategory category;
    private String priceTier;
}
