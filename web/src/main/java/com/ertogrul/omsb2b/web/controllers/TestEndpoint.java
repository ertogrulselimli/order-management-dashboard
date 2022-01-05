package com.ertogrul.omsb2b.web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestEndpoint {

    @Autowired
    SimpMessagingTemplate messagingTemplate;


    @GetMapping(value = "/test/{name}/{count}")
    public void test(
            @PathVariable("name") final String name,
            @PathVariable("count") final Integer count){
          messagingTemplate.convertAndSendToUser(name,"/queue/balance",count);
    }




}
