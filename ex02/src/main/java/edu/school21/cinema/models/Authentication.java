package edu.school21.cinema.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authentication {
    private Long id;
    private String ip;
    private Timestamp date;
    private Long userId;

    public Authentication(String ip, Long userId) {
        this.ip = ip;
        this.userId = userId;
    }
}
