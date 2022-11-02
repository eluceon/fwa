package edu.school21.cinema.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private Long id;
    private String name;
    private String path;
    private Long size;
    private String mime;
    private Long userId;

    public Image(String name, String path, Long size, String mime, Long userId) {
        this.name = name;
        this.path = path;
        this.size = size;
        this.mime = mime;
        this.userId = userId;
    }
}
