package com.example.auth.event;


import com.example.auth.eventDto.UserResignedEventDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResignedEvent extends AbstractEvent{
    private UserResignedEventDto payload;

    public UserResignedEvent() {
        super();
    }

    public UserResignedEvent(UserResignedEventDto payload) {
        super(payload);
        this.payload = payload;
    }
}
