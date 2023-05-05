package Erofeev.MusicStoreCWsem4.controllers;

import Erofeev.MusicStoreCWsem4.dto.*;
import Erofeev.MusicStoreCWsem4.entities.Item;
import Erofeev.MusicStoreCWsem4.entities.ItemImage;
import Erofeev.MusicStoreCWsem4.errors.ItemNotFoundException;
import Erofeev.MusicStoreCWsem4.mappers.ItemMapper;
import Erofeev.MusicStoreCWsem4.mappers.OrderMapper;
import Erofeev.MusicStoreCWsem4.services.ItemImageService;
import Erofeev.MusicStoreCWsem4.services.ItemService;
import Erofeev.MusicStoreCWsem4.services.OrderService;
import Erofeev.MusicStoreCWsem4.utils.ImageNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final OrderService orderService;
    private final ItemService itemService;
    private final ImageNameService nameService;
    private final ItemImageService itemImageService;
    private final ItemMapper itemMapper = ItemMapper.INSTANCE;
    private final OrderMapper orderMapper = OrderMapper.INSTANCE;
    private final String UPLOAD_DIRECTORY = "C:/musicstore/images";

    @GetMapping("/items")
    public List<ListItemDTO> getItems(@RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "limit", defaultValue = "20") int limit) {
        return itemService.findAll(page, limit).stream()
                .map(itemMapper::convertItemToListItemDTO).collect(Collectors.toList());
    }

    @GetMapping("/orders")
    public List<OrderListItemDTO> getOrders(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "limit", defaultValue = "20") int limit) {
        return orderService.getAll(page, limit).stream()
                .map(orderMapper::convertOrderToOrderListItemDTO).collect(Collectors.toList());
    }

    @GetMapping("/order/{id}")
    public OrderDTO getOrder(@PathVariable("id") long id) {
        return orderMapper.convertOrderToOrderDTO(orderService.getById(id));
    }

    @GetMapping("/item/{id}")
    public ItemDTO getItem(@PathVariable("id") long id) throws ItemNotFoundException {
        return itemMapper.convertItemToItemDTO(itemService.findById(id));
    }

    @PostMapping("/add_item")
    public ResponseEntity<Long> addNewItem(@RequestBody NewItemDTO newItemDTO) {
        long id = itemService.save(itemMapper.convertNewItemDTOToItem(newItemDTO));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping("/load_image/{id}")
    public void loadImage(@PathVariable("id") long itemId, @RequestBody MultipartFile file)
            throws IOException, ItemNotFoundException {
        String imageName = nameService.generate(file.getContentType());
        Path filenameAndPath = Paths.get(UPLOAD_DIRECTORY + "/items", imageName);
        Files.write(filenameAndPath, file.getBytes());

        Item item = itemService.findById(itemId);
        ItemImage itemImage = new ItemImage();
        itemImage.setItem(item);
        itemImage.setUrl("/items/" + imageName);

        itemImageService.save(itemImage);
    }
}
