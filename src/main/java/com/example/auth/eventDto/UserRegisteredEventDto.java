package com.example.auth.eventDto;


import com.example.auth.domain.User;

import lombok.Getter;

@Getter
public class UserRegisteredEventDto extends AbstractDto{
    private int userId;

    public UserRegisteredEventDto() {
    }

    public UserRegisteredEventDto(User user) {
        this.userId = user.getId();
    }

    public int getUserId() {
        return userId;
    }
}
