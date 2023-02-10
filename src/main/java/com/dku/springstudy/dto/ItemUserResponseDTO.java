package com.dku.springstudy.dto;

import com.dku.springstudy.model.User;
import lombok.Data;

@Data
public class ItemUserResponseDTO {
    private String id;
    private String name;
    private String url;

    public ItemUserResponseDTO(User user) {
        id = user.getId();
        name = user.getUsername();
        url = user.getProfileImageUrl();
    }
}
