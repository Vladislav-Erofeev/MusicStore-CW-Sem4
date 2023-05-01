package Erofeev.MusicStoreCWsem4.controllers;

import Erofeev.MusicStoreCWsem4.dto.ListItemDTO;
import Erofeev.MusicStoreCWsem4.entities.Person;
import Erofeev.MusicStoreCWsem4.errors.ErrorResponse;
import Erofeev.MusicStoreCWsem4.errors.ItemNotFoundException;
import Erofeev.MusicStoreCWsem4.errors.PersonNotFoundException;
import Erofeev.MusicStoreCWsem4.mappers.ItemMapper;
import Erofeev.MusicStoreCWsem4.services.CartService;
import Erofeev.MusicStoreCWsem4.utils.AuthenticatedPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final AuthenticatedPersonService authenticatedPersonService;
    private final ItemMapper itemMapper = ItemMapper.INSTANCE;

    @GetMapping
    public List<ListItemDTO> getCart() throws PersonNotFoundException {
        Person person = authenticatedPersonService.getPerson();
        return cartService.getCartByPerson(person.getId()).stream()
                .map(itemMapper::convertItemToListItemDTO).collect(Collectors.toList());
    }

    @GetMapping("/in_cart/{id}")
    public Boolean isItemInCart(@PathVariable("id") long itemId) {
        Person person = authenticatedPersonService.getPerson();
        if (cartService.isInCart(person.getId(), itemId) == null)
            return false;
        return true;
    }

    @PostMapping("/add/{id}")
    public void add(@PathVariable("id") long itemId) throws PersonNotFoundException, ItemNotFoundException {
        Person person = authenticatedPersonService.getPerson();
        cartService.addItemToCart(person.getId(), itemId);
    }

    @DeleteMapping("/delete/{id}")
    public void remove(@PathVariable("id") long itemId) throws PersonNotFoundException, ItemNotFoundException {
        Person person = authenticatedPersonService.getPerson();
        cartService.removeItemFromCart(person.getId(), itemId);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> personNotFound(PersonNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> itemNotFound(ItemNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
