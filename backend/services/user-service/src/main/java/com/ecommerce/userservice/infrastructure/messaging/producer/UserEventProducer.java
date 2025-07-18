package com.ecommerce.userservice.infrastructure.messaging.producer;

import com.ecommerce.userservice.domain.event.UserRegisteredEvent;
import com.ecommerce.userservice.domain.model.User;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducer {
    private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;

    public UserEventProducer(KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishUserRegistered(User user) {
        UserRegisteredEvent event = new UserRegisteredEvent(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName());
        kafkaTemplate.send("user.registered", event);
    }
}
