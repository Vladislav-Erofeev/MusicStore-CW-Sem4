package Erofeev.MusicStoreCWsem4.repositories;

import Erofeev.MusicStoreCWsem4.entities.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
}
