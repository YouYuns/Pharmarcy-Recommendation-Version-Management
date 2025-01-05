package com.recommend.shyun.pharmacy.service;


import com.recommend.shyun.api.dto.DocumentDto;
import com.recommend.shyun.api.dto.KakaoApiResponseDto;
import com.recommend.shyun.api.service.KakaoAddressSearchService;
import com.recommend.shyun.direction.dto.OutputDto;
import com.recommend.shyun.direction.entity.Direction;
import com.recommend.shyun.direction.service.Base62Service;
import com.recommend.shyun.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class PharmacyRecommendationService {

    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";

    private final KakaoAddressSearchService kakaoAddressSearchService;

    private final DirectionService  directionService;

    private final Base62Service base62Service;

    @Value("${pharmacy.recommendation.base.url}")
    private String baseUrl;



    public List<OutputDto> recommendPharmacyList(String address){
        //고객이 주소를 입력하면 kakao api를 통해서 위치기반으로 응답값을 받아온다.
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        //해당 Objects Null -> 더이상 처리 안한다.
        //또 DocumentList가 empList로올수도있음
        if(Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())){
            log.error("[PharmacyRecommendationService recommendPharmacyList fail] input address: {}", address);
            return Collections.emptyList();
        }

        //고객에게 실제로 길안내 가까운 약국 여러개의 고객이 입력한 주소중에 고객이 현재 가깝게 위치한 주소를 가져오기때문에 get(0)
        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);


        //위치 거리 계산 알고리즘으로 계산한 가까운 약국 리스트 Direction정보를 가져온다
        //공공기관을 이용
        // List<Direction> directionList =  directionService.buildDirectionList(documentDto);


        //카카오 api를 이용
        List<Direction> directionList =  directionService.buildDirectionListByCategoryApi(documentDto);

        //그리고 해당 Direction정보를 DB에 저장
        return directionService.saveAll(directionList)
                .stream().map(this::converToOutputDto)
                .collect(Collectors.toList());
    }

    private OutputDto converToOutputDto(Direction direction){
        return OutputDto.builder()
                .pharmacyAddress(direction.getTargetAddress())
                .pharmacyName(direction.getTargetPharmacyName())
                .directionUrl(baseUrl + base62Service.encodeDirectionId(direction.getId()))
                .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }
}
