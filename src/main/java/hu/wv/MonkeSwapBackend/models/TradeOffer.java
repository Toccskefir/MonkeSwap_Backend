package hu.wv.MonkeSwapBackend.models;

import jakarta.persistence.*;

@Entity
@Table(
        name = "trade_offers",
        uniqueConstraints = {@UniqueConstraint(name = "UK_ITEMS",columnNames = {"offered_item_id", "incoming_item_id"})}
)
public class TradeOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "offered_item_id")
    private Long offeredItemId;

    @Column(name = "incoming_item_id")
    private Long incomingItemId;

    private String comment;
}
