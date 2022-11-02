package edu.school21.cinema.services;

import edu.school21.cinema.models.Image;
import edu.school21.cinema.repositories.ImageRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    public void save(Image image) {
        imageRepository.save(image);
    }

    @Override
    public List<Image> findImagesByUserId(Long id) {
        return imageRepository.findAllByUserId(id);
    }
}
