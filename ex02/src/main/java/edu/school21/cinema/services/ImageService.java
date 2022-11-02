package edu.school21.cinema.services;

import edu.school21.cinema.models.Image;

import java.util.List;

public interface ImageService {
    void save(Image image);
    List<Image> findImagesByUserId(Long id);
}
