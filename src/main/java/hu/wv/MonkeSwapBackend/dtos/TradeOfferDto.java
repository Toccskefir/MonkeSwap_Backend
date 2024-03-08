package hu.wv.MonkeSwapBackend.dtos;

import hu.wv.MonkeSwapBackend.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeOfferDto {
    private Long id;
    private Item offeredItem;
    private Item incomingItem;
    private String comment;
}
