package Erofeev.MusicStoreCWsem4.security;

import Erofeev.MusicStoreCWsem4.entities.Person;
import Erofeev.MusicStoreCWsem4.errors.NotUniqueEmailException;
import Erofeev.MusicStoreCWsem4.repositories.PersonRepository;
import Erofeev.MusicStoreCWsem4.utils.ImageNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final ImageNameService nameService;

    @Transactional
    public String register(RegistrationRequest registrationRequest, String url) throws NotUniqueEmailException {
        String password = passwordEncoder.encode(registrationRequest.getPassword());
        if (personRepository.findByMail(registrationRequest.getMail()).isPresent())
            throw new NotUniqueEmailException("the user already exists");
        Person person = Person.builder()
                .name(registrationRequest.getName())
                .phone(registrationRequest.getPhone())
                .role("ROLE_USER")
                .mail(registrationRequest.getMail())
                .lastname(registrationRequest.getLastname())
                .url(url)
                .city(registrationRequest.getCity())
                .password(password)
                .build();
        personRepository.save(person);
        return jwtUtil.generateToken(person);
    }

    public String authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<Person> optionalPerson = personRepository.findByMail(request.getLogin());
        if (optionalPerson.isEmpty())
            throw new UsernameNotFoundException("User not found");

        Person person = optionalPerson.get();
        String token = jwtUtil.generateToken(person);
        return token;
    }
}
