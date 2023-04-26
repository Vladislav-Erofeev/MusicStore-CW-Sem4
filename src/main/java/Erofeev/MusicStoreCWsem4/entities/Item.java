package Erofeev.MusicStoreCWsem4.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "body")
    private String body;

    @Column(name = "count")
    private int count;

    @Column(name = "price")
    private double price;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private ItemCategory category;

    @OneToMany(mappedBy = "item")
    private List<ItemImage> images;

    public void addImage(ItemImage itemImage) {
        if (images == null)
            images = new LinkedList<>();
        images.add(itemImage);
    }
}
