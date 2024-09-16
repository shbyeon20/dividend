package com.example.dividen.service;

import com.example.dividen.model.Auth;
import com.example.dividen.model.MemberDetails;
import com.example.dividen.persist.MemberRepository;
import com.example.dividen.persist.entity.MemberEntity;
import com.example.dividen.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    // Id가 signIn 시도되었을때 그에 맞는 entity를 찾고 details를 불러옴
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return MemberDetails.fromEntity(
                memberRepository.findByUsername(username).orElseThrow(()
                        -> new UsernameNotFoundException("could not find user -> " + username))
        );
    }

    // Id가 sign up되었을때 그에 맞는 entity와 detail을 만들어서 저장해줌
    public MemberDetails register(Auth.SignUpRequest member) {
        if(memberRepository.existsByUsername(member.getUserName())){
            throw new RuntimeException("이미 사용중인 아이디 입니다");
            // PW는 암호화 과정이 필수적임
        }else{
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            MemberEntity.fromSignUp(member);
            return MemberDetails.fromEntity(memberRepository.save(MemberEntity.fromSignUp(member)));
        }
    }


    // Dao의 credential의 유효성을 검증하고 유효하다면 토큰을 발행
    public MemberEntity authenticate(Auth.SignInRequest member) {
        MemberEntity memberEntity = memberRepository.findByUsername(member.getUserName()).orElseThrow(()
                -> new UsernameNotFoundException("could not find user -> " + member.getUserName()));

        if(!passwordEncoder.matches(member.getPassword(), memberEntity.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }

        return memberEntity;
    }
}
