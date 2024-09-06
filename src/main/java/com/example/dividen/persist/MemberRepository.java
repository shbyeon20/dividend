package com.example.dividen.persist;

import com.example.dividen.persist.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    //회원가입된 Credential 찾기
    Optional<MemberEntity> findByUsername(String userName);

    // 회원가입전 아이디 존재여부 찾기
    boolean existsByUsername(String userName);
}
