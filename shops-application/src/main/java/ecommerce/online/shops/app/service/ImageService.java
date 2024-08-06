package ecommerce.online.shops.app.service;

import ecommerce.online.shops.app.dto.ImageDto;
import ecommerce.online.shops.app.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
