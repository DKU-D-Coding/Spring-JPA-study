package com.dku.springstudy.service;

import com.dku.springstudy.dto.ItemsDTO;
import com.dku.springstudy.model.*;
import com.dku.springstudy.repository.ItemsRepository;
import com.dku.springstudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ItemsService {
    private final ItemsRepository itemsRepository;
    private final UserRepository userRepository;


    public List<Items> index(){
        List<Items> items = itemsRepository.findAll();
        return items ;
    }

    public void itemUpload(ItemsDTO itemsDTO, String userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException("오류 : 없는 사용자 입니다."));
/**
        Items items = Items.builder()
                .user(user)
                .title(itemsDTO.getTitle())
                .price(itemsDTO.getPrice())
                .intro(itemsDTO.getIntro())
                .category(Category.valueOf(itemsDTO.getCategory()))
                .itemStatus(ItemStatus.SELLING)
                .build();

        itemsRepository.save(items);
 */
    }
}
