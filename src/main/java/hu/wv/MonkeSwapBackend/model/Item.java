package hu.wv.MonkeSwapBackend.model;

import hu.wv.MonkeSwapBackend.enums.ItemCategory;
import hu.wv.MonkeSwapBackend.enums.ItemState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offeredItem")
    private List<TradeOffer> offeredItems;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "incomingItem")
    private List<TradeOffer> incomingItems;
}
