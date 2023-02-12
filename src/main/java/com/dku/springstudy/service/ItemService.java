package com.dku.springstudy.service;

import com.dku.springstudy.domain.ImageFile;
import com.dku.springstudy.domain.Item;
import com.dku.springstudy.domain.ItemLike;
import com.dku.springstudy.domain.Member;
import com.dku.springstudy.dto.ItemDetailsDto;
import com.dku.springstudy.dto.ItemDto;
import com.dku.springstudy.enums.Category;
import com.dku.springstudy.enums.ItemStatus;
import com.dku.springstudy.exception.KarrotException;
import com.dku.springstudy.repository.jpa.ItemLikeRepository;
import com.dku.springstudy.repository.jpa.ItemRepository;
import com.dku.springstudy.security.AuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;
    private final ImageFileService imageFileService;
    private final ItemLikeRepository itemLikeRepository;
    private final S3Upload s3Upload;

    @Transactional
    public Long addItem(Item item){
        itemRepository.save(item);
        return item.getId();
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new KarrotException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "해당 상품이 존재하지 않습니다."));
    }

    public List<ItemDto> findByMember(Member member) {
        List<Item> sellerItems = itemRepository.findByMember(member);
        return sellerItems.stream()
                .map(i -> new ItemDto(
                        i.getId(),
                        i.getImages().stream().map(ImageFile::getImageUrl).collect(Collectors.toList()),
                        i.getTitle(),
                        i.getContent(),
                        i.getPrice(),
                        i.getLikes().size(),
                        i.getStatus())
                )
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateItemStatus(Long id, ItemStatus itemStatus) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new KarrotException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "존재하지 않는 상품입니다."));
        item.changeStatus(itemStatus);
    }


    public ItemDto transferPreviousItemInfo(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new KarrotException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "존재하지 않는 상품입니다."));
        return new ItemDto(
                item.getId(),
                item.getImages().stream().map(ImageFile::getImageUrl).collect(Collectors.toList()),
                item.getTitle(),
                item.getContent(),
                item.getPrice(),
                item.getLikes().size(),
                item.getStatus());
    }

    @Transactional
    public void updateItem(Long itemId, String title, String content, Category category, int price, MultipartFile[] updateMultipartFiles) throws IOException {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new KarrotException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "존재하지 않는 상품입니다."));

        imageFileService.deleteAllImages(item.getImages());

        List<ImageFile> imageFiles = new ArrayList<>();

        if(updateMultipartFiles.length != 0){
            for(MultipartFile multipartFile : updateMultipartFiles){
                String imagePath = s3Upload.upload(multipartFile);
                ImageFile imageFile = ImageFile.createImageFile(imagePath);
                imageFiles.add(imageFile);
                imageFile.updateItem(item);
                imageFileService.save(imageFile);
            }
        }

        item.updateItem(title, content, category, price, imageFiles);


    }

    @Transactional
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    public List<ItemDto> findFavoriteItems() {
        Member currentMember = AuthenticationProvider.getCurrentMember();

        List<ItemLike> itemLikesByMember = itemLikeRepository.findByMember(currentMember);

        List<Item> result = itemLikesByMember.stream()
                .map(ItemLike::getItem)
                .collect(Collectors.toList());

        return result.stream()
                .map(i -> new ItemDto(
                        i.getId(),
                        i.getImages().stream().map(ImageFile::getImageUrl).collect(Collectors.toList()),
                        i.getTitle(),
                        i.getContent(),
                        i.getPrice(),
                        i.getLikes().size(),
                        i.getStatus())
                )
                .collect(Collectors.toList());

    }
}
