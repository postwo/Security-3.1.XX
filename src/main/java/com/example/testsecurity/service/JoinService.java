package com.example.testsecurity.service;

import com.example.testsecurity.dto.User;
import com.example.testsecurity.entity.UserEntity;
import com.example.testsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(User user){

        //db에 이미 동일한 usernmae을 가지 회원이 존재하는지? 검증을한후 데이터를 넣어준다


        //앞단에서 받은 user정보를 entity로 변형해줘야 한다
        UserEntity data = new UserEntity();//빈 객체생성

        data.setUsername(user.getUsername()); // dto정보를 entity로변형
        data.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        data.setRole("ROLE_USER");

        userRepository.save(data); // 엔티티변형한거를 save한다
    }

}
