package com.dku.springstudy.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dku.springstudy.dto.BoardDTO;
import com.dku.springstudy.model.Board;
import com.dku.springstudy.model.Category;
import com.dku.springstudy.model.User;
import com.dku.springstudy.repository.BoardRepository;
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
public class BoardService {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public void write(BoardDTO boardDTO, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("멤버가 없습니다"));
        Board board = Board.builder()
                .user(user)
                .title(boardDTO.getTitle())
                .price(boardDTO.getPrice())
                .intro(boardDTO.getIntro())
                .category(Category.BEAUTY)
                .build();
        System.out.println(boardDTO.getIntro());
        boardRepository.save(board);
    }

    public List<String> multipleUpload(List<MultipartFile> multipartFile) {
        List<String> fileNameList = new ArrayList<>();

        multipartFile.forEach(file -> {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
            }
            fileNameList.add(amazonS3.getUrl(bucket, fileName).toString());
        });
        return fileNameList;
    }

    public void deleteFile(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
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

    public List<Board> index(){
        List<Board> boards = boardRepository.findAll();
        return boards;
    }
}
