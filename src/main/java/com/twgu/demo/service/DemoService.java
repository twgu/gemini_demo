package com.twgu.demo.service;

import com.twgu.demo.common.DemoUtil;
import com.twgu.demo.common.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemoService {
    private final JwtUtil jwtUtil;

    public String createAccessToken(String userName, String role) {
        if (DemoUtil.isNullOrEmpty(userName)) {
            throw new RuntimeException("userName은 null 이거나 비어있을 수 없습니다.");
        }

        if (DemoUtil.isNullOrEmpty(role)) {
            throw new RuntimeException("role은 null 이거나 비어있을 수 없습니다.");
        }

        return jwtUtil.createAccessToken(userName, role);
    }

    public String createRefreshToken(String userName) {
        if (DemoUtil.isNullOrEmpty(userName)) {
            throw new RuntimeException("userName은 null 이거나 비어있을 수 없습니다.");
        }

        return jwtUtil.createRefreshToken(userName);
    }

    public String validateToken(String token) {
        JwtUtil.TokenStatus status = jwtUtil.validateToken(token);

        switch (status) {
            case EXPIRED -> {
                return "만료된 토큰";
            }
            case INVALID -> {
                return "비정상 토큰";
            }
        }

        return "정상 토큰";
    }
}
