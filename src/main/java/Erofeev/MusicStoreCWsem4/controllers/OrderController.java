package Erofeev.MusicStoreCWsem4.controllers;

import Erofeev.MusicStoreCWsem4.dto.NewOrderDTO;
import Erofeev.MusicStoreCWsem4.dto.OrderDTO;
import Erofeev.MusicStoreCWsem4.dto.OrderListItemDTO;
import Erofeev.MusicStoreCWsem4.entities.Person;
import Erofeev.MusicStoreCWsem4.errors.PersonNotFoundException;
import Erofeev.MusicStoreCWsem4.mappers.OrderMapper;
import Erofeev.MusicStoreCWsem4.services.OrderService;
import Erofeev.MusicStoreCWsem4.utils.AuthenticatedPersonService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final AuthenticatedPersonService authenticatedPersonService;
    private final OrderMapper orderMapper = OrderMapper.INSTANCE;

    @GetMapping("/all")
    public List<OrderListItemDTO> getOrders() {
        Person person = authenticatedPersonService.getPerson();
        return orderService.getAll(person.getId()).stream()
                .map(orderMapper::convertOrderToOrderListItemDTO).collect(Collectors.toList());
    }

    @PostMapping("/create")
    public void createOrder(@RequestBody NewOrderDTO newOrderDTO) throws PersonNotFoundException {
        Person person = authenticatedPersonService.getPerson();
        orderService.createNewOrder(person.getId(), newOrderDTO.getAddress());
    }

    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable("id") long id) {
        return orderMapper.convertOrderToOrderDTO(orderService.getById(id));
    }
}
