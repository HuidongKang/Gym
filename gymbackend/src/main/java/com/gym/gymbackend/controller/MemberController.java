package com.gym.gymbackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gym.gymbackend.domain.Member;
import com.gym.gymbackend.service.MemberService;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @DeleteMapping("/api/checkExp/")
    public ResponseEntity<?> checkExp(){
        memberService.만료회원삭제();
        return new ResponseEntity<>(null, HttpStatus.OK);
        
    }

    @DeleteMapping("/api/removeMember/{id}")
    public ResponseEntity<?> removeMember(@PathVariable int id){
        memberService.회원삭제(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping("/api/modifyMember/")
    public ResponseEntity<?> modifyMember(@RequestBody HashMap<String, Object> req){
        String message = memberService.회원정보수정(req);
        if (message.equals("성공")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/manageMember/load")
    public ResponseEntity<?> loadMember(){
        return new ResponseEntity<>(memberService.회원정보로드(), HttpStatus.OK);
    }

    @PutMapping("/api/extension/")
    public ResponseEntity<?> extension(@RequestBody HashMap<String, Object> req) {
        String message = memberService.회원권연장(req);
        if (message.equals("성공")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/login/{password}")
    public ResponseEntity<?> checkin(@PathVariable String password) {

        List<Member> members = memberService.로그인(password);

        if (members.size() >= 1) {
            return new ResponseEntity<>(members, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("등록된 회원이 아닙니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/login/{password}/{id}")
    public ResponseEntity<?> dupCheckin(@PathVariable Map<String, String> body) {
        String password = body.get("password");
        Long id = Long.parseLong(body.get("id"));

        Member member = memberService.중복로그인(password, id);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PostMapping("/api/signup/")
    public ResponseEntity<?> signup(@RequestBody HashMap<String, Object> req) {

        String message = memberService.회원가입(req);
        if (message.equals("성공")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }
}
