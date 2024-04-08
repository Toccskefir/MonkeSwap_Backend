package hu.wv.MonkeSwapBackend.dtos;

import hu.wv.MonkeSwapBackend.enums.ItemCategory;
import hu.wv.MonkeSwapBackend.enums.ItemState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;
    private String title;
    private byte[] itemPicture;
    private String description;
    private Integer views;
    private List<Long> reports;
    private ItemState state;
    private ItemCategory category;
    private Integer priceTier;
    private Long userId;
}
