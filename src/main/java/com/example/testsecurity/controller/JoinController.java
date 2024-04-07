package com.example.testsecurity.controller;

import com.example.testsecurity.dto.User;
import com.example.testsecurity.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {

    @Autowired
    private JoinService joinService;

    @GetMapping("/join")
    public String joinForm(){
        return "join";
    }

    @PostMapping("/joinProc")
    public String join(User user){


        System.out.println(user);

        joinService.join(user);

        return "redirect:/login"; // 회원가입 완료되면 login으로 이동
    }
}
