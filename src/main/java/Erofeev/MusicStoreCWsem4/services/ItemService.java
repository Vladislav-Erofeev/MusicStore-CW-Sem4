package Erofeev.MusicStoreCWsem4.services;

import Erofeev.MusicStoreCWsem4.dto.ListItemDTO;
import Erofeev.MusicStoreCWsem4.dto.NewItemDTO;
import Erofeev.MusicStoreCWsem4.entities.ItemCategory;
import Erofeev.MusicStoreCWsem4.mappers.ItemMapper;
import Erofeev.MusicStoreCWsem4.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper = ItemMapper.INSTANCE;

    @Transactional
    public void save(NewItemDTO newItemDTO) {
        itemRepository.save(itemMapper.convertNewItemDTOToItem(newItemDTO));
    }

    public List<ListItemDTO> findAll(int page, int limit, String category) {
        if (category == "")
            return itemRepository.findAll(PageRequest.of(page, limit)).stream()
                    .map(itemMapper::convertItemToListItemDTO).collect(Collectors.toList());
        ItemCategory category1 = Enum.valueOf(ItemCategory.class, category);
        return itemRepository.findByCategory(category1, PageRequest.of(page, limit)).stream()
                .map(itemMapper::convertItemToListItemDTO).collect(Collectors.toList());
    }

}
