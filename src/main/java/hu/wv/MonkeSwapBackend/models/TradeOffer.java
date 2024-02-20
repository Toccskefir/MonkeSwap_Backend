package hu.wv.MonkeSwapBackend.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(
        name = "trade_offers",
        uniqueConstraints = {@UniqueConstraint(name = "UK_ITEMS",columnNames = {"offered_item_id", "incoming_item_id"})}
)
@Data
public class TradeOffer {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "offered_item_id")
    private Item offeredItem;

    @ManyToOne
    @JoinColumn(name = "incoming_item_id")
    private Item incomingItem;

    private String comment;
}
