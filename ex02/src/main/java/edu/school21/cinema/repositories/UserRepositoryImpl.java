package edu.school21.cinema.repositories;

import edu.school21.cinema.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM users WHERE id=?",
                new BeanPropertyRowMapper<>(User.class), id).stream().findFirst();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
           PreparedStatement ps = connection
                   .prepareStatement(
                           "INSERT INTO users(first_name, last_name, phone_number, email, password) VALUES (?, ?, ?, ?, ?)",
                           Statement.RETURN_GENERATED_KEYS
                   );
           ps.setString(1, user.getFirstName());
           ps.setString(2, user.getLastName());
           ps.setString(3, user.getPhoneNumber());
           ps.setString(4, user.getEmail());
           ps.setString(5, user.getPassword());
           return ps;
        }, keyHolder);
        user.setId((Long) keyHolder.getKeys().get("id"));
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(
                "UPDATE users SET first_name=?, last_name=?, phone_number=? , email=?, password=? WHERE id=?",
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getPassword(),
                user.getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM users WHERE id=?", id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM users WHERE email=?",
                new BeanPropertyRowMapper<>(User.class), email).stream().findFirst();
    }
}
