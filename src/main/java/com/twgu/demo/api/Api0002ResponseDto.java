package com.twgu.demo.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Api0002ResponseDto {
    private String accessToken;
    private String refreshToken;
}
