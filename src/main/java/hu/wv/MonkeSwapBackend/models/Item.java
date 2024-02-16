package hu.wv.MonkeSwapBackend.models;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @NotNull
    private String state;

    @NotNull
    private String category;

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

    //CONSTRUCTORS
    public Item() {

    }

    public Item(Long id,
                String title,
                String itemPicture,
                String description,
                Integer views,
                Integer reports,
                String state,
                String category,
                String priceTier,
                User userId) {
        this.id = id;
        this.title = title;
        this.itemPicture = itemPicture;
        this.description = description;
        this.views = views;
        this.reports = reports;
        this.state = state;
        this.category = category;
        this.priceTier = priceTier;
        this.userId = userId;
    }

    public Item(String title,
                String itemPicture,
                String description,
                Integer views,
                Integer reports,
                String state,
                String category,
                String priceTier,
                User userId) {
        this.title = title;
        this.itemPicture = itemPicture;
        this.description = description;
        this.views = views;
        this.reports = reports;
        this.state = state;
        this.category = category;
        this.priceTier = priceTier;
        this.userId = userId;
    }
}
