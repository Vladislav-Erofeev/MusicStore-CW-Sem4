package Erofeev.MusicStoreCWsem4.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationRequest {
    private String name;

    private String lastname;

    private String phone;

    private String mail;

    private String password;

    private String city;
}
