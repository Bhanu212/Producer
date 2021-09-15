package com.demo.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;



@Service
public class ProducerService {
	
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    public ListenableFuture<SendResult<String, String>> Send(String topic,JSONObject data) {
    	System.out.println(topic+"=="+data.toString());
    	return kafkaTemplate.send(topic,data.toString() );

    }
}