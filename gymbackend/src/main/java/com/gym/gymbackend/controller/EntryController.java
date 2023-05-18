package com.gym.gymbackend.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gym.gymbackend.domain.dto.MemberInfoDto;
import com.gym.gymbackend.service.EntryService;
import com.gym.gymbackend.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
public class EntryController {
    
    @Autowired
    private MemberService memberService;
    @Autowired
    private EntryService entryService;

    @PostMapping("/api/checkin/check")
    public ResponseEntity<?> check(@RequestBody HashMap<String, Object> req){

        String message = entryService.출입체크(req);
        if(message.equals("성공")){
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else{
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
    
    @GetMapping("/api/checkin/{memberId}/info")
    public ResponseEntity<?> loadMemberInfo(@PathVariable int memberId){
        MemberInfoDto memberDto = memberService.회원정보불러오기(memberId);
        return new ResponseEntity<>(memberDto, HttpStatus.OK);
    }
}
