package Erofeev.MusicStoreCWsem4.security;

import Erofeev.MusicStoreCWsem4.entities.Person;
import Erofeev.MusicStoreCWsem4.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByMail(username);
        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");
        PersonDetails personDetails = new PersonDetails(person.get());
        return personDetails;
    }
}
