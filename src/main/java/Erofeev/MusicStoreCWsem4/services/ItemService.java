package Erofeev.MusicStoreCWsem4.services;

import Erofeev.MusicStoreCWsem4.entities.Item;
import Erofeev.MusicStoreCWsem4.entities.ItemCategory;
import Erofeev.MusicStoreCWsem4.errors.ItemNotFoundException;
import Erofeev.MusicStoreCWsem4.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public long save(Item newItem) {
        return itemRepository.save(newItem).getId();
    }

    public List<Item> findAll(int page, int limit, String category) {
        if (category.isBlank())
            return itemRepository.findAll(PageRequest.of(page, limit)).getContent();
        ItemCategory category1 = Enum.valueOf(ItemCategory.class, category);
        return itemRepository.findByCategory(category1, PageRequest.of(page, limit));
    }

    public Item findById(long id) throws ItemNotFoundException {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty())
            throw new ItemNotFoundException("Item with id = " + id + " not found");
        return optionalItem.get();
    }

}
