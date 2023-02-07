package com.dku.springstudy.service;

import com.dku.springstudy.dto.ItemsDTO;
import com.dku.springstudy.dto.ItemsResponseDTO;
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
    public List<ItemsResponseDTO> index(){
        List<Items> items = itemsRepository.findItemsWithImages();
        List<ItemsResponseDTO> result = items.stream()
                .map(b -> new ItemsResponseDTO(b))
                .collect(Collectors.toList());
        return result;
    }
}
