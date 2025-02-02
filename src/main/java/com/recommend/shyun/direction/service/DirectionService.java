package com.recommend.shyun.direction.service;


import com.recommend.shyun.api.dto.DocumentDto;
import com.recommend.shyun.api.service.KakaoCategorySearchService;
import com.recommend.shyun.direction.entity.Direction;
import com.recommend.shyun.direction.repository.DirectionRepository;
import com.recommend.shyun.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DirectionService {
    // 약국 최대 검색 갯수
    private static final int MAX_SEARCH_COUNT = 3;
    // 반경 10km 이내의 약국들만 검색해준다
    private static final double RADIUS_KM = 10.0;

    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";

    private final PharmacySearchService pharmacySearchService;

    private final DirectionRepository directionRepository;

    private final KakaoCategorySearchService  kakaoCategorySearchService;

    private final Base62Service base62Service;


    @Transactional
    public List<Direction> saveAll(List<Direction> directionList) {
        if(CollectionUtils.isEmpty(directionList)) return Collections.emptyList();
        return directionRepository.saveAll(directionList);
    }


    public String findDirectionUrlById(String encodedId){
        Long decodedId = base62Service.decodeDirectionId(encodedId);
        Direction direction =  directionRepository.findById(decodedId).orElse(null);

        String params = String.join(",", direction.getTargetPharmacyName(),
                String.valueOf(direction.getTargetLatitude()), String.valueOf(direction.getTargetLongitude()));

        String result = UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL + params).toUriString();

        return result;
    }

    //documentDto는 Kakao Api에서 사용했던 Api이다
    //kakao에서 호출한 String address정보의 위도 경도를 갖고와서 찾는다
    public List<Direction> buildDirectionList(DocumentDto documentDto){

    
        //고객의 direction 정보없으면 빈리스트 전달
        if(Objects.isNull(documentDto)) return Collections.emptyList();

        //고객에 대한 정보는 DocumentDto에 다들어있다
        //kakao api호출시 담긴 고객정보들
        
        // 약국 데이터 조회
       return  pharmacySearchService.searchPharmacyDtoList()
                .stream().map(pharmacyDto ->
                                Direction.builder()
                                        .inputAddress(documentDto.getAddressName())
                                        .inputLatitude(documentDto.getLatitude())
                                        .inputLongitude(documentDto.getLongitude())
                                        .targetAddress(pharmacyDto.getPharmacyAddress())
                                        .targetLatitude(pharmacyDto.getLatitude())
                                        .targetLongitude(pharmacyDto.getLongitude())
                                        .targetPharmacyName(pharmacyDto.getPharmacyName())
                                        .distance(calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(),
                                                pharmacyDto.getLatitude(), pharmacyDto.getLongitude()))
                                .build())
                .filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
        //거리계산 알고리즘을 이용하여, 고객와 약국 사이의 거리를 계산하고 sort
    }



    // pharmacy search by category kakao api
    public List<Direction> buildDirectionListByCategoryApi(DocumentDto inputDocumentDto) {
        if(Objects.isNull(inputDocumentDto)) return Collections.emptyList();

        return kakaoCategorySearchService
                .requestPharmacyCategorySearch(inputDocumentDto.getLatitude(), inputDocumentDto.getLongitude(), RADIUS_KM)
                .getDocumentList()
                .stream().map(resultDocumentDto ->
                        Direction.builder()
                                .inputAddress(inputDocumentDto.getAddressName())
                                .inputLatitude(inputDocumentDto.getLatitude())
                                .inputLongitude(inputDocumentDto.getLongitude())
                                .targetPharmacyName(resultDocumentDto.getPlaceName())
                                .targetAddress(resultDocumentDto.getAddressName())
                                .targetLatitude(resultDocumentDto.getLatitude())
                                .targetLongitude(resultDocumentDto.getLongitude())
                                .distance(resultDocumentDto.getDistance() * 0.001) // km 단위
                                .build())
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }



    // Haversine formula 위도경도 계산 알고리즘 그냥 구글에서 가져온 계산식
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        //첫번째 위도 경도는 고객의 주소

        //두번째는 약국의 위도 경도
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}
