package edu.school21.cinema.services;

import edu.school21.cinema.models.Authentication;
import edu.school21.cinema.models.User;
import edu.school21.cinema.repositories.AuthenticationRepository;
import edu.school21.cinema.repositories.ImageRepository;
import edu.school21.cinema.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;
    private final ImageRepository imageRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, AuthenticationRepository authenticationRepository,
                           ImageRepository imageRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationRepository = authenticationRepository;
        this.imageRepository = imageRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public boolean signUp(String firstName, String lastName,
                          String phoneNumber, String email, String password, String ip) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return false;
        }
        User user = new User(firstName, lastName, phoneNumber, email, passwordEncoder.encode(password));
        userRepository.save(user);
        authenticationRepository.save(new Authentication(ip, user.getId()));
        return true;
    }

    @Override
    @Transactional
    public User signIn(String email, String password, String ip) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }
        authenticationRepository.save(new Authentication(ip, user.getId()));
        user.setImages(imageRepository.findAllByUserId(user.getId()));
        user.setAuthentications(authenticationRepository.findAllByUserId(user.getId()));
        return user;
    }
}
