package Erofeev.MusicStoreCWsem4.services;

import Erofeev.MusicStoreCWsem4.entities.Item;
import Erofeev.MusicStoreCWsem4.entities.Person;
import Erofeev.MusicStoreCWsem4.errors.ItemNotFoundException;
import Erofeev.MusicStoreCWsem4.errors.PersonNotFoundException;
import Erofeev.MusicStoreCWsem4.repositories.ItemRepository;
import Erofeev.MusicStoreCWsem4.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final PersonRepository personRepository;
    private final ItemRepository itemRepository;

    public List<Item> findCartByPerson(long personId) throws PersonNotFoundException {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        if (optionalPerson.isEmpty())
            throw new PersonNotFoundException("person not found");
        Person person = optionalPerson.get();
        Hibernate.initialize(person.getCart());
        return person.getCart();
    }

    @Transactional
    public void addItemToCart(long personId, long itemId) throws PersonNotFoundException, ItemNotFoundException {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalPerson.isEmpty())
            throw new PersonNotFoundException("person not found");
        if (optionalItem.isEmpty())
            throw new ItemNotFoundException("item not found");

        Person person = optionalPerson.get();
        Item item = optionalItem.get();
        person.addToCart(item);
        item.addToCart(person);
    }

    @Transactional
    public void removeItemFromCart(long personId, long itemId) throws PersonNotFoundException, ItemNotFoundException {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalPerson.isEmpty())
            throw new PersonNotFoundException("person not found");
        if (optionalItem.isEmpty())
            throw new ItemNotFoundException("item not found");

        Person person = optionalPerson.get();
        Item item = optionalItem.get();
        person.getCart().remove(item);
        item.getPersonList().remove(person);
    }

    public Item isInCart(long personId, long itemId) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        Person person = optionalPerson.get();
        return person.getCart().stream().filter(i -> i.getId() == itemId).findAny().orElse(null);
    }
}
