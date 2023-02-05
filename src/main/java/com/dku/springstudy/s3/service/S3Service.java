package com.dku.springstudy.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dku.springstudy.domain.Product;
import com.dku.springstudy.exception.CustomException;
import com.dku.springstudy.exception.ErrorCode;
import com.dku.springstudy.s3.domain.File;
import com.dku.springstudy.s3.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final FileRepository fileRepository;

    @Transactional
    public List<String> uploadFiles(List<MultipartFile> multipartFile, Product product) {
        List<String> fileUrlList = new ArrayList<>();

        multipartFile.forEach(file -> {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new CustomException(ErrorCode.FILE_UPLOAD_FAIL);
            }

            File saveFile = File.builder()
                    .product(product)
                    .url(getUrl(fileName))
                    .fileName(fileName)
                    .build();

            fileRepository.save(saveFile);
            fileUrlList.add(getUrl(fileName));
        });
        return fileUrlList;
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        List<String> extensions = Arrays.asList(".jpg", ".jpeg", ".png");
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));

        if (!fileName.contains(fileExtension)) {
            throw new CustomException(ErrorCode.FILE_EXTENSION_NOT_SUPPORT);
        }

        return fileName.substring(fileName.lastIndexOf("."));
    }

    private String getUrl(String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }
}
