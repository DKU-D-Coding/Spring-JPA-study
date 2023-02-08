package com.dku.springstudy.service;

import com.dku.springstudy.domain.ImageFile;
import com.dku.springstudy.repository.jpa.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageFileService {

    private final ImageFileRepository imageFileRepository;

    @Transactional
    public void save(ImageFile imageFile){
        imageFileRepository.save(imageFile);
    }

    @Transactional
    public void deleteAllImages(List<ImageFile> imageFiles){
        for (ImageFile imageFile : imageFiles) {
            imageFileRepository.delete(imageFile);
        }
    }
}
