package edu.school21.cinema.services;

import edu.school21.cinema.models.Authentication;
import edu.school21.cinema.repositories.AuthenticationRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationRepository authenticationRepository;
    @Override
    public List<Authentication> findAllByUserId(Long id) {
        return authenticationRepository.findAllByUserId(id);
    }
}
