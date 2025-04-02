package com.example.auth.eventDto;

import com.example.auth.domain.User;

import lombok.Getter;

@Getter
public class UserResignedEventDto extends AbstractDto{
    private int userId;

    public UserResignedEventDto() {
    }

    public UserResignedEventDto(User user) {
        this.userId = user.getId();
    }
}
