package hu.wv.MonkeSwapBackend.dtos;

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
    private Long offeredItem;
    private Long incomingItem;
    private String comment;
}
