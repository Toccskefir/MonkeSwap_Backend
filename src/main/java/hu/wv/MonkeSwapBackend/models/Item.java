package hu.wv.MonkeSwapBackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "items")
public class Item {
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

    @Column(name = "user_id")
    private Long userId;
}
