package edu.school21.cinema.services;

import edu.school21.cinema.models.User;

public interface UserService {
    boolean signUp(String firstName, String lastName, String phoneNumber, String email, String password, String ip);
    User signIn(String email, String password, String ip);
}
