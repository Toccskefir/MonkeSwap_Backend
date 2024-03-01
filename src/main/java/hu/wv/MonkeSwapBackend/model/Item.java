package hu.wv.MonkeSwapBackend.model;

import hu.wv.MonkeSwapBackend.enums.ItemCategory;
import hu.wv.MonkeSwapBackend.enums.ItemState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "items")
@Data
public class Item {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @Column(name = "item_picture")
    @NotNull
    private String itemPicture;

    @NotNull
    private String description;

    @NotNull
    private Integer views;

    @NotNull
    private Integer reports;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ItemState state;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ItemCategory category;

    @Column(name = "price_tier")
    @NotNull
    private String priceTier;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @OneToMany(mappedBy = "offeredItem")
    private List<TradeOffer> offeredItems;

    @OneToMany(mappedBy = "incomingItem")
    private List<TradeOffer> incomingItems;
}
