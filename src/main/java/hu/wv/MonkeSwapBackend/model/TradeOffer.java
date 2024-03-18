package hu.wv.MonkeSwapBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "trade_offers",
        uniqueConstraints = {@UniqueConstraint(name = "UK_ITEMS",columnNames = {"offered_item_id", "incoming_item_id"})}
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeOffer {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "offered_item_id")
    private Item offeredItem;

    @ManyToOne
    @JoinColumn(name = "incoming_item_id")
    private Item incomingItem;

    private String comment;
}
