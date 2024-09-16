package com.example.dividen.web;


import com.example.dividen.model.Auth;
import com.example.dividen.persist.entity.MemberEntity;
import com.example.dividen.security.JwtTokenProvider;
import com.example.dividen.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    // 동일 아이디가 존재하는지 체크하고 존재하지 않는다면 등록
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SignUpRequest request) {
        return ResponseEntity.ok(memberService.register(request));

    }

    // credential의 유효성을 검증하고 유효하다면 토큰을 발행
    @PostMapping("/signin")
    public ResponseEntity<?> signup(@RequestBody Auth.SignInRequest request) {

        MemberEntity memberEntity = memberService.authenticate(request);

        String token = jwtTokenProvider.generateToken(memberEntity.getUsername(),
                memberEntity.getRoles());

        return ResponseEntity.ok(token);
    }
}
