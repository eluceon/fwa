package edu.school21.cinema.repositories;

import edu.school21.cinema.models.Authentication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AuthenticationRepositoryImpl implements AuthenticationRepository {
    private JdbcTemplate jdbcTemplate;

    public AuthenticationRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate((dataSource));
    }

    @Override
    public List<Authentication> findAllByUserId(Long id) {
        return jdbcTemplate.query("SELECT * FROM authentications WHERE user_id=?",
                new BeanPropertyRowMapper<>(Authentication.class),
                id
        );
    }

    @Override
    public Optional<Authentication> findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM authentications WHERE id=?",
                new BeanPropertyRowMapper<>(Authentication.class), id).stream().findFirst();
    }

    @Override
    public List<Authentication> findAll() {
        return jdbcTemplate.query("SELECT * FROM authentications", new BeanPropertyRowMapper<>(Authentication.class));
    }

    @Override
    public void save(Authentication authentication) {
        jdbcTemplate.update(
                "INSERT INTO authentications(ip, user_id) VALUES (?, ?)",
                authentication.getIp(),
                authentication.getUserId()
        );
    }

    @Override
    public void update(Authentication authentication) {
        jdbcTemplate.update(
                "UPDATE authentications SET ip=?, date=?, user_id=? WHERE id=?",
                authentication.getIp(),
                new Timestamp(new Date().getTime()),
                authentication.getUserId(),
                authentication.getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM authentications WHERE id=?", id);
    }
}
