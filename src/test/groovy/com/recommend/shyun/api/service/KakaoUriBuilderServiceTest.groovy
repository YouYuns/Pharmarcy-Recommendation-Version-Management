package com.recommend.shyun.api.service

import com.recommend.shyun.api.service.KakaoUriBuilderService
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.nio.charset.StandardCharsets

// 컨테이너를 띄워서 통합테스트진행하기위한 어노테이션
@SpringBootTest
class KakaoUriBuilderServiceTest extends Specification{

    private KakaoUriBuilderService kakaoUriBuilderService;
    def setup() {
        kakaoUriBuilderService = new KakaoUriBuilderService();
    }

    def "buildUriByAddressSearch - 한글 파라미터의 경우 정상적으로 인코딩" (){
        given:
        String address ="서울 노원구"
        def charset = StandardCharsets.UTF_8

        when:
        def uri = kakaoUriBuilderService.buildUriByAddress(address)
        def decodeResult = URLDecoder.decode(uri.toString(), charset)

        then:
        decodeResult == "https://dapi.kakao.com/v2/local/search/address.json?query=서울 노원구"


    }
}
