package Erofeev.MusicStoreCWsem4.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "item_image")
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;
}
