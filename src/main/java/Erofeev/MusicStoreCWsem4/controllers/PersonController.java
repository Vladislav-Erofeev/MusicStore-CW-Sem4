package Erofeev.MusicStoreCWsem4.controllers;

import Erofeev.MusicStoreCWsem4.dto.PersonDTO;
import Erofeev.MusicStoreCWsem4.mappers.PersonMapper;
import Erofeev.MusicStoreCWsem4.security.PersonDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @GetMapping("/profile")
    public PersonDTO getPerson() {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return personMapper.convertPersonToPersonDTO(personDetails.getPerson());
    }
}
