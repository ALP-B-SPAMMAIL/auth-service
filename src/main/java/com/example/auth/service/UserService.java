package com.example.auth.service; 


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.auth.domain.User;
import com.example.auth.dto.request.RegisterRequest;
import com.example.auth.event.UserRegisteredEvent;
import com.example.auth.eventDto.UserRegisteredEventDto;
import com.example.auth.kafka.KafkaProducer;
import com.example.auth.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducer kafkaProducer;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, KafkaProducer kafkaProducer){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.kafkaProducer=kafkaProducer;
    }

    public User findByUserFigureId(String userFigureId) {
        return userRepository.findByUserFigureId(userFigureId).get();
    }

    public String register(RegisterRequest registerRequest){
        User user = new User();
        user.setBirthDate(registerRequest.birthDate);
        user.setGender(registerRequest.gender);
        user.setInterest(registerRequest.interest);
        user.setJob(registerRequest.job);
        user.setName(registerRequest.name);
        user.setPassword(passwordEncoder.encode(registerRequest.password));
        user.setUserFigureId(registerRequest.userFigureId);
        user.setEmail(registerRequest.email);
        userRepository.save(user);

        try {
            kafkaProducer.publish(new UserRegisteredEvent(new UserRegisteredEventDto(user)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "error";
        }   

        return "{\"success\": true, \"userId\": " + user.getId() + "}";
    }

    // public User findByPhoneNumber(String phoneNumber) {
    //     return userRepository.findByPhoneNumber(phoneNumber)
    //             .orElseThrow(() -> new PhoneNumberNotFoundException(AuthErrorCode.PHONE_NUMBER_NOT_FOUND));
    // }
}
