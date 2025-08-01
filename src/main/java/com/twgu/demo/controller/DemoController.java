package com.twgu.demo.controller;

import com.twgu.demo.api.Api0002RequestDto;
import com.twgu.demo.api.Api0002ResponseDto;
import com.twgu.demo.api.Api0003RequestDto;
import com.twgu.demo.common.JwtUtil;
import com.twgu.demo.dto.ResponseDto;
import com.twgu.demo.service.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "DemoController")
@RestController
@RequiredArgsConstructor
public class DemoController {
    private final DemoService demoService;

    @Operation(summary = "API 호출 테스트")
    @PostMapping("/api-0001/test")
    public ResponseDto<Void> api0001() {
        ResponseDto<Void> res = new ResponseDto<>();
        res.setCode(HttpStatus.OK.value());
        res.setMessage(HttpStatus.OK.getReasonPhrase());
        res.setData(null);
        return res;
    }

    @Operation(summary = "JWT 발급 테스트")
    @PostMapping("/api-0002/getToken")
    public ResponseDto<Api0002ResponseDto> api0002(@RequestBody Api0002RequestDto req) {
        Api0002ResponseDto data = new Api0002ResponseDto();
        data.setAccessToken(demoService.createAccessToken(req.getUserName(), req.getRole()));
        data.setRefreshToken(demoService.createRefreshToken(req.getUserName()));

        ResponseDto<Api0002ResponseDto> res = new ResponseDto<>();
        res.setCode(HttpStatus.OK.value());
        res.setMessage(HttpStatus.OK.getReasonPhrase());
        res.setData(data);
        return res;
    }

    @Operation(summary = "JWT 유효성 검사 테스트")
    @PostMapping("/api-0003/validateToken")
    public ResponseDto<Void> api0003(@RequestBody Api0003RequestDto req) {
        String message = demoService.validateToken(req.getToken());

        ResponseDto<Void> res = new ResponseDto<>();
        res.setCode(HttpStatus.OK.value());
        res.setMessage(message);
        res.setData(null);
        return res;
    }
}
