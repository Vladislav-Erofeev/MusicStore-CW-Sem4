package Erofeev.MusicStoreCWsem4.mappers;

import Erofeev.MusicStoreCWsem4.dto.PersonDTO;
import Erofeev.MusicStoreCWsem4.entities.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
    PersonDTO convertPersonToPersonDTO(Person person);
}
