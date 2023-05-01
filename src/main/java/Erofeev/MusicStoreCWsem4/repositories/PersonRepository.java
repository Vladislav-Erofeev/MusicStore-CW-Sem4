package Erofeev.MusicStoreCWsem4.repositories;

import Erofeev.MusicStoreCWsem4.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByMail(String mail);
}
