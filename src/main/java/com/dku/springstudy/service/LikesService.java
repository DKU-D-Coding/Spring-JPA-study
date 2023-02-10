package com.dku.springstudy.service;

import com.dku.springstudy.dto.ItemsResponseDTO;
import com.dku.springstudy.model.Items;
import com.dku.springstudy.model.Likes;
import com.dku.springstudy.model.User;
import com.dku.springstudy.repository.ItemsRepository;
import com.dku.springstudy.repository.LikesRepository;
import com.dku.springstudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LikesService {

    private final UserRepository userRepository;
    private final LikesRepository likesRepository;
    private final ItemsRepository itemsRepository;

    @Transactional
    public String clickLikes(String userId, Long itemId){
       User user = userRepository.findById(userId).orElseThrow(()->new IllegalStateException("유저 없음 오류"));
        Items items = itemsRepository.findById(itemId).orElseThrow(()->new IllegalStateException("아이템 없음 오류"));

        Likes likes = likesRepository.findByItemsAndUser(items, user);
        if(likes != null){
            likes.deleteLike();
            likesRepository.delete(likes);
            return "좋아요 취소";
        }
        else{
            Likes like = Likes.builder()
                    .user(user)
                    .items(items)
                    .build();
            likesRepository.save(like);
            like.addLike();
            return "좋아요 완료";
        }
    }

    public List<ItemsResponseDTO> likeItems(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalStateException("유저 없음 오류"));
        List<Likes> likes = likesRepository.findByUser(user);
        List<Items> items = itemsRepository.findByLikesIn(likes);
        List<ItemsResponseDTO> result = items.stream()
                .map(b->new ItemsResponseDTO(b))
                .collect(Collectors.toList());
        return result;

    }
}
