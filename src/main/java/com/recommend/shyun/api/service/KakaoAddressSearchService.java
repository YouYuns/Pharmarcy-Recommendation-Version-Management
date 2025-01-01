package com.recommend.shyun.api.service;


import com.recommend.shyun.api.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAddressSearchService {

    private final RestTemplate restTemplate;

    private final KakaoUriBuilderService kakaoUriBuilderService;

    @Value("${KAKAO_REST_API_KEY}")
    private String kakaoRestApiKey;



    //재처리 어노테이션
    @Retryable(
            value = { RuntimeException.class },
            maxAttempts = 2,
            backoff = @Backoff(delay = 2000) //2초 딜레이
    )

    public KakaoApiResponseDto requestAddressSearch(String address){
        if(ObjectUtils.isEmpty(address)) return null;

        URI uri = kakaoUriBuilderService.buildUriByAddress(address);
        //uri는 만들었고 restapi에 kakao restapi key를 헤더에 담아야된다.

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
        HttpEntity httpEntity = new HttpEntity(headers);

        // kakao api 호출
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoApiResponseDto.class).getBody();

    }

    //재처리가 모두실패했을경우 노테이션
    //* 주의사항 원래 메서드의 리턴타입을 맞춰줘야된다.
    @Recover
    public KakaoApiResponseDto recover(RuntimeException e, String address){
        log.error("All the retries failed. address: {}, error: {}", address, e.getMessage());
        return null;

    }

}

