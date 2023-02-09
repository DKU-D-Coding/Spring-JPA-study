package com.dku.springstudy.service;

import com.dku.springstudy.dto.ItemsDTO;
import com.dku.springstudy.dto.ItemsResponseDTO;
import com.dku.springstudy.dto.ItemsStatusDTO;
import com.dku.springstudy.dto.user.ImageResponseDTO;
import com.dku.springstudy.model.*;
import com.dku.springstudy.repository.ItemsRepository;
import com.dku.springstudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ItemsService {
    private final ItemsRepository itemsRepository;
    private final UserRepository userRepository;

    public List<ItemsResponseDTO> index(){
        List<Items> items = itemsRepository.findItemsWithImages();
        List<ItemsResponseDTO> result = items.stream()
                .map(b -> new ItemsResponseDTO(b))
                .collect(Collectors.toList());
        return result;
    }

    @Transactional
    public void changeItemStatus(Long itemId, ItemsStatusDTO itemsStatusDTO) {
        Items item = itemsRepository.findById(itemId).orElseThrow(()->new IllegalStateException("상품없음 오류"));
        item.changeStatus(itemsStatusDTO);
    }

    public List<ItemsResponseDTO> findMyItems(String userID) {
        User user = userRepository.findById(userID).orElseThrow(()-> new IllegalStateException("사용자 없음 오류"));
        List<Items> myItems = itemsRepository.findByUser(user);
        List<ItemsResponseDTO> result = myItems.stream()
                .map(b->new ItemsResponseDTO(b))
                .collect(Collectors.toList());
        return result;
    }
}
