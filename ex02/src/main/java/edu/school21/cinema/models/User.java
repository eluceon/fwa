package edu.school21.cinema.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class User {
    Long id;
    String firstName;
    String lastName;
    String phoneNumber;
    String email;
    String password;
    private List<Authentication> authentications;
    private List<Image> images;

    public User(String firstName, String lastName, String phoneNumber, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }
}
