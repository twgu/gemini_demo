package com.twgu.demo.controller;

import com.twgu.demo.dto.ResponseDto;
import com.twgu.demo.service.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
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
}
