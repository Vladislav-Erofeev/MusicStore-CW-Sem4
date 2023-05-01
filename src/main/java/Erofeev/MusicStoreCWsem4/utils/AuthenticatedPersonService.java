package Erofeev.MusicStoreCWsem4.utils;

import Erofeev.MusicStoreCWsem4.entities.Person;
import Erofeev.MusicStoreCWsem4.security.PersonDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedPersonService {
    public Person getPerson() {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return personDetails.getPerson();
    }
}
