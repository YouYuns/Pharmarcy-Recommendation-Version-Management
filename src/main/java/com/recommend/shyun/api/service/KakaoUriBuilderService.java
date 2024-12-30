package com.recommend.shyun.api.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class KakaoUriBuilderService {

    //kakao dev 요청 상수값
    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    public URI buildUriByAddress(String address){
        //String을 붙여서 만들어도되지만
        //가독성있게 UriComponentsBUilder 사용
        UriComponentsBuilder uriBuilder =  UriComponentsBuilder.fromUriString(KAKAO_LOCAL_SEARCH_ADDRESS_URL);

        //query에 주소값을 담아서 요청보낸다.
        uriBuilder.queryParam("query", address);

        //URI 생성 ENCODE는 한글이나 특수문자, 공백 등 해석할수없는 것들을 UTF-8로 인코딩한다.
        URI uri = uriBuilder.build().encode().toUri();
        log.info("[KakaoUriBuilderService] address: {}, uri {}", address, uri);

        return uri;
    }

}
