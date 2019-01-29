package com.recommendation.update_service.service;

import com.recommendation.update_service.dto.UpdateMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UpdateListenerService {

    @KafkaListener(topics = "UPDATE",group = "group_update",containerFactory = "updateKafkaListenerFactory")
    public void listenUpdate(UpdateMessage updateMessage)
    {

    }

}
