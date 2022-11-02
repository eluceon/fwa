package edu.school21.cinema.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.cinema.repositories.*;
import edu.school21.cinema.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@PropertySource("classpath:/application.properties")
@ComponentScan("edu.school21.cinema.repositories")
@Configuration
public class ApplicationConfig {
    @Value("${db.url}")
    private String url;
    @Value("${db.user}")
    private String username;
    @Value("${db.password}")
    private String password;
    @Value("${db.driver.name}")
    private String driverClassName;
    @Value("${storage.path}")
    private String imgPath;

    public String getImgPath() {
        return imgPath;
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driverClassName);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserRepository usersRepositoryImplBean() {
        return new UserRepositoryImpl(dataSource());
    }

    @Bean
    public AuthenticationRepository authenticationRepositoryImplBean() {
        return new AuthenticationRepositoryImpl(dataSource());
    }

    @Bean
    public ImageRepository imageRepositoryImplBean() {
        return new ImageRepositoryImpl(dataSource());
    }

    @Bean
    UserService userServiceBean() {
        return new UserServiceImpl(usersRepositoryImplBean(), authenticationRepositoryImplBean(),
                imageRepositoryImplBean(), passwordEncoderBean());
    }

    @Bean
    ImageService ImageServiceBean() {
        return new ImageServiceImpl(imageRepositoryImplBean());
    }

    @Bean
    AuthenticationService AuthenticationServiceBean() {
        return new AuthenticationServiceImpl(authenticationRepositoryImplBean());
    }
}
