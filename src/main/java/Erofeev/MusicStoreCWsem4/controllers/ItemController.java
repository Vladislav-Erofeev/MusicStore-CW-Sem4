package Erofeev.MusicStoreCWsem4.controllers;

import Erofeev.MusicStoreCWsem4.dto.ItemDTO;
import Erofeev.MusicStoreCWsem4.dto.ListItemDTO;
import Erofeev.MusicStoreCWsem4.dto.NewItemDTO;
import Erofeev.MusicStoreCWsem4.errors.ErrorResponse;
import Erofeev.MusicStoreCWsem4.errors.ItemNotFoundException;
import Erofeev.MusicStoreCWsem4.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/all")
    public List<ListItemDTO> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "limit", defaultValue = "20") int limit,
                                    @RequestParam(name = "category", defaultValue = "") String category) {
        return itemService.findAll(page, limit, category);
    }

    @PostMapping("/add")
    public void addNewItem(@RequestBody NewItemDTO newItemDTO) {
        itemService.save(newItemDTO);
    }

    @GetMapping("/{id}")
    public ItemDTO findById(@PathVariable("id") long id) throws ItemNotFoundException {
        return itemService.findById(id);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> itemNotFound(ItemNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
