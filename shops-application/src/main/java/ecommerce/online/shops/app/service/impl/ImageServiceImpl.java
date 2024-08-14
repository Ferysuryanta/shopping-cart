package ecommerce.online.shops.app.service.impl;

import ecommerce.online.shops.app.dto.ImageDto;
import ecommerce.online.shops.app.exception.ResourceNotFoundException;
import ecommerce.online.shops.app.model.Image;
import ecommerce.online.shops.app.repo.ImageRepository;
import ecommerce.online.shops.app.service.ImageService;
import ecommerce.online.shops.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProductService productService;
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository::delete, () -> {
                    throw new ResourceNotFoundException("No image found with id: " + id);
                });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        var product = productService.getProductById(productId);
        List<ImageDto> imageDtos = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFilename(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(file.getBytes());
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                var savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                imageRepository.save(savedImage);

                var imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setFilename(savedImage.getFilename());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                imageDtos.add(imageDto);

            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return imageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        var image = getImageById(imageId);
        try {
            image.setFilename(file.getOriginalFilename());
            image.setFilename(file.getOriginalFilename());
            image.setImage(file.getBytes());
            imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
