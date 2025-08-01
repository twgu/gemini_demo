package com.twgu.demo.config;

import com.twgu.demo.common.DemoUtil;
import com.twgu.demo.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Hidden
@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        log.error("Unhandled RuntimeException occurred", ex);

        String message = DemoUtil.isNullOrEmpty(ex.getMessage())
                ? HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
                : ex.getMessage();

        ResponseDto<Void> res = new ResponseDto<>();
        res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        res.setMessage(message);
        res.setData(null);
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
