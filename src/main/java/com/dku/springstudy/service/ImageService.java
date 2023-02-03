package com.dku.springstudy.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dku.springstudy.dto.ItemsDTO;
import com.dku.springstudy.model.*;
import com.dku.springstudy.repository.ImageRepository;
import com.dku.springstudy.repository.ItemsRepository;
import com.dku.springstudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ImageService {
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.cloud.aws.s3.targetURL}")
    private String target;

    private final ItemsRepository itemsRepository;
    private final ImageRepository imageRepository;
    private final AmazonS3 amazonS3;
    private final UserRepository userRepository;
    @Transactional
    public Long multipleUpload(List<MultipartFile> multipartFile, String userId, ItemsDTO itemsDTO) {
        List<Images> fileNameList = new ArrayList<>();

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("멤버가 없습니다"));

        Items items = Items.builder()
                .user(user)
                .images(fileNameList)
                .title(itemsDTO.getTitle())
                .price(itemsDTO.getPrice())
                .intro(itemsDTO.getIntro())
                .category(Category.valueOf(itemsDTO.getCategory()))
                .itemStatus(ItemStatus.SELLING)
                .build();

        multipartFile.forEach(file -> {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            Images images = Images.builder()
                    .user(user)
                    .url(amazonS3.getUrl(bucket, fileName).toString())
                    .build();
            fileNameList.add(images);
            items.addItemWithImage(images);

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
            }
        });
        itemsRepository.save(items);
        return items.getId();
    }

    public void deleteFile(List<Images> fileName) {
        fileName.forEach(file->
                {
                    String url = file.getUrl();
                    url = url.replace(target,"");
                    amazonS3.deleteObject(new DeleteObjectRequest(bucket, url));
                });

    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }

    @Transactional
    public void multipleModify(List<MultipartFile> multipartFile, String userId, ItemsDTO itemsDTO, Long itemId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("멤버가 없습니다"));
        Items items = itemsRepository.findById(itemId).orElseThrow(() -> new IllegalStateException("상품이 없습니다"));

        List<Images> beforeImages = items.getImages();
        if (multipartFile.size()!=0) {
            deleteFile(beforeImages);
            imageRepository.deleteByItemsId(items.getId());
        }

        multipartFile.forEach(file -> {
                    String fileNameModify = createFileName(file.getOriginalFilename());
                    ObjectMetadata objectMetadata = new ObjectMetadata();
                    objectMetadata.setContentLength(file.getSize());
                    objectMetadata.setContentType(file.getContentType());

                    Images images = Images.builder()
                            .user(user)
                            .url(amazonS3.getUrl(bucket, fileNameModify).toString())
                            .build();
                    items.addItemWithImage(images);
                });
        items.modifyItems(itemsDTO);
    }
}
