package Erofeev.MusicStoreCWsem4.services;

import Erofeev.MusicStoreCWsem4.entities.ItemImage;
import Erofeev.MusicStoreCWsem4.repositories.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemImageService {
    private final ItemImageRepository itemImageRepository;

    @Transactional
    public void save(ItemImage itemImage) {
        itemImageRepository.save(itemImage);
    }
}
