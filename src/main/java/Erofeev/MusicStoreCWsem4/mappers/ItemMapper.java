package Erofeev.MusicStoreCWsem4.mappers;

import Erofeev.MusicStoreCWsem4.dto.ListItemDTO;
import Erofeev.MusicStoreCWsem4.dto.NewItemDTO;
import Erofeev.MusicStoreCWsem4.entities.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    Item convertNewItemDTOToItem(NewItemDTO newItemDTO);

    ListItemDTO convertItemToListItemDTO(Item item);
}
