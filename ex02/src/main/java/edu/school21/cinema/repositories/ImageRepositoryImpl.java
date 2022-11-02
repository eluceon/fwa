package edu.school21.cinema.repositories;

import edu.school21.cinema.models.Image;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ImageRepositoryImpl implements ImageRepository {
    private JdbcTemplate jdbcTemplate;

    public ImageRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate((dataSource));
    }

    @Override
    public Optional findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM images WHERE id=?",
                new BeanPropertyRowMapper<>(Image.class), id).stream().findFirst();
    }

    @Override
    public List findAll() {
        return jdbcTemplate.query("SELECT * FROM images", new BeanPropertyRowMapper<>(Image.class));
    }

    @Override
    public void save(Image image) {
        jdbcTemplate.update(
                "INSERT INTO images(name, path, size, mime, user_id) VALUES (?, ?, ?, ?, ?)",
                image.getName(),
                image.getPath(),
                image.getSize(),
                image.getMime(),
                image.getUserId()
        );
    }

    @Override
    public void update(Image image) {
        jdbcTemplate.update(
                "UPDATE images SET name=?, path=?, size=? , mime=?, date=?, user_id=? WHERE id=?",
                image.getName(),
                image.getPath(),
                image.getSize(),
                image.getMime(),
                new Timestamp(new Date().getTime()),
                image.getUserId(),
                image.getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM images WHERE id=?", id);
    }

    @Override
    public List<Image> findAllByUserId(Long id) {
        return jdbcTemplate.query("SELECT * FROM images WHERE user_id=?",
                new BeanPropertyRowMapper<>(Image.class),
                id
        );
    }
}
