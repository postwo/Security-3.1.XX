package com.example.testsecurity.repository;

import com.example.testsecurity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    // jpa 메소드 existsByUsername 이거를 강제로 커스텀
    // where 절로 username이 존재하는지 쿼리문을 날려서 존재하면 true 존재하지 않으면 false로 보낸다
    boolean existsByUsername(String username);


    //이것도 로그인 검증 로직을 커스텀
    UserEntity findByUsername(String username);
}
