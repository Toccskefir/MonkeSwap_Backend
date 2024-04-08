package hu.wv.MonkeSwapBackend.model;

import hu.wv.MonkeSwapBackend.enums.ItemCategory;
import hu.wv.MonkeSwapBackend.enums.ItemState;
import hu.wv.MonkeSwapBackend.utils.LongListConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @Column(name = "item_picture", length = 1000000)
    @Lob
    @NotNull
    private byte[] itemPicture;

    @NotNull
    private String description;

    @NotNull
    private Integer views;

    @Convert(converter = LongListConverter.class)
    @Column(nullable = false)
    private List<Long> reports = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @NotNull
    private ItemState state;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ItemCategory category;

    @Column(name = "price_tier")
    @NotNull
    private Integer priceTier;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offeredItem")
    private List<TradeOffer> offeredItems;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "incomingItem")
    private List<TradeOffer> incomingItems;
}
