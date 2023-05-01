package Erofeev.MusicStoreCWsem4.dto;

import Erofeev.MusicStoreCWsem4.entities.Item;
import Erofeev.MusicStoreCWsem4.entities.OrderStatus;
import Erofeev.MusicStoreCWsem4.entities.Person;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private long id;

    private String address;

    private String orderDate;

    private String status;

    private float price;

    private PersonDTO owner;

    private List<ItemDTO> items;
}
