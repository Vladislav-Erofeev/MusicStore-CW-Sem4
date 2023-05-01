package Erofeev.MusicStoreCWsem4.services;

import Erofeev.MusicStoreCWsem4.entities.Item;
import Erofeev.MusicStoreCWsem4.entities.Order;
import Erofeev.MusicStoreCWsem4.entities.OrderStatus;
import Erofeev.MusicStoreCWsem4.entities.Person;
import Erofeev.MusicStoreCWsem4.errors.PersonNotFoundException;
import Erofeev.MusicStoreCWsem4.repositories.ItemRepository;
import Erofeev.MusicStoreCWsem4.repositories.OrderRepository;
import Erofeev.MusicStoreCWsem4.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
//    private final ItemRepository itemRepository;
    private final PersonRepository personRepository;

    public List<Order> getAll(long personId) {
        return orderRepository.findByOwnerId(personId);
    }

    @Transactional
    public void createNewOrder(long personId, String address) throws PersonNotFoundException {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        if (optionalPerson.isEmpty())
            throw new PersonNotFoundException("person not found");

        Person person = optionalPerson.get();
        Order order = new Order();
        order.setItems(person.getCart());
        order.setAddress(address);
        float sum = 0;
        for(Item x : person.getCart())
            sum += x.getPrice();
        order.setPrice(sum);
        order.setStatus(OrderStatus.CREATED);
        order.setOwner(person);
        order.setOrderDate(Date.from(Instant.now().plus(3, ChronoUnit.DAYS)));

        person.getCart().forEach(item -> {item.setCount(item.getCount() - 1);});
        person.setCart(null);
        order.getItems().forEach(item -> {item.addOrder(order);});
        order.getItems().forEach(item -> {item.getPersonList().remove(person);});

        personRepository.save(person);
        orderRepository.save(order);
    }

    public Order getById(long id) {
        return orderRepository.findById(id).get();
    }
}
