package Erofeev.MusicStoreCWsem4.controllers;

import Erofeev.MusicStoreCWsem4.dto.ItemDTO;
import Erofeev.MusicStoreCWsem4.dto.ListItemDTO;
import Erofeev.MusicStoreCWsem4.dto.NewItemDTO;
import Erofeev.MusicStoreCWsem4.entities.Item;
import Erofeev.MusicStoreCWsem4.entities.ItemImage;
import Erofeev.MusicStoreCWsem4.errors.ErrorResponse;
import Erofeev.MusicStoreCWsem4.errors.ItemNotFoundException;
import Erofeev.MusicStoreCWsem4.mappers.ItemMapper;
import Erofeev.MusicStoreCWsem4.services.ItemImageService;
import Erofeev.MusicStoreCWsem4.services.ItemService;
import Erofeev.MusicStoreCWsem4.utils.ImageNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;
    private final ImageNameService nameService;
    private final ItemImageService itemImageService;
    private final ItemMapper itemMapper = ItemMapper.INSTANCE;
    private final String UPLOAD_DIRECTORY = "C:/musicstore/images";

    @GetMapping("/all")
    public List<ListItemDTO> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "limit", defaultValue = "20") int limit,
                                    @RequestParam(value = "category", defaultValue = "") String category) {
        return itemService.findAll(page, limit, category).stream()
                .map(itemMapper::convertItemToListItemDTO).collect(Collectors.toList());
    }

    @PostMapping("/add")
    public ResponseEntity<Long> addNewItem(@RequestBody NewItemDTO newItemDTO) {
        long id = itemService.save(itemMapper.convertNewItemDTOToItem(newItemDTO));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ItemDTO findById(@PathVariable("id") long id) throws ItemNotFoundException {
        return itemMapper.convertItemToItemDTO(itemService.findById(id));
    }

    @PostMapping("/load_image/{id}")
    public void loadImage(@PathVariable("id") long itemId,@RequestBody MultipartFile file)
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

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> itemNotFound(ItemNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
