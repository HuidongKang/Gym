package com.gym.gymbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.gymbackend.service.MembershipService;

@RestController
public class MembershipController {
    
    @Autowired
    MembershipService membershipService;

    @GetMapping("/api/checkMembership/")
    public ResponseEntity<?> checkMembership(){
        int count = membershipService.회원권생성확인();
        String message = "성공";
        if(count < 4){
            message = membershipService.회원권생성();
        }
        
        if(message.equals("성공")){
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        
        
    }
}
