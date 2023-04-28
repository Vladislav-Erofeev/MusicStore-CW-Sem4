package Erofeev.MusicStoreCWsem4.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String lastname;

    private String phone;

    private String mail;

    private String password;

    private String city;

    private String url;

    private String role;

    @ManyToMany(mappedBy = "personList")
    private List<Item> cart;

    public void addToCart(Item item) {
        if (cart == null)
            cart = new LinkedList<>();
        cart.add(item);
    }
}
