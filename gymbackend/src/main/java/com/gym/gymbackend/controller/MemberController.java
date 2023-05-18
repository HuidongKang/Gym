package com.gym.gymbackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gym.gymbackend.domain.Member;
import com.gym.gymbackend.service.MemberService;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/api/checkin/{password}")
    public ResponseEntity<?> checkin(@PathVariable String password) {

        List<Member> members = memberService.로그인(password);

        if (members.size() == 1) {
            return new ResponseEntity<>(members.get(0), HttpStatus.OK);
        } else if(members.size() == 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else{
            return new ResponseEntity<>(members, HttpStatus.OK);
        }

    }

    @GetMapping("/api/checkin/{password}/{id}")
    public ResponseEntity<?> dupCheckin(@PathVariable Map<String, String> body) {
        String password = body.get("password");
        Long id = Long.parseLong(body.get("id"));

        Member member = memberService.중복로그인(password, id);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PostMapping("/api/signup/")
    public ResponseEntity<?> signup(@RequestBody HashMap<String, Object> req) {

        String message = memberService.회원가입(req);
        if(message.equals("성공")){
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else{
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
