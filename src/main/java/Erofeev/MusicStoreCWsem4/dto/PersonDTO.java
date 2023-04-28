package Erofeev.MusicStoreCWsem4.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonDTO {
    private long id;

    private String name;

    private String lastname;

    private String phone;

    private String mail;

    private String password;

    private String city;

    private String url;

    private String role;
}
