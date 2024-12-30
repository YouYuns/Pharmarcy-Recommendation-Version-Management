package com.recommend.shyun.direction.service;


import com.recommend.shyun.api.dto.DocumentDto;
import com.recommend.shyun.direction.entity.Direction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DirectionService {



    //documentDto는 Kakao Api에서 사용했던 Api이다
    //kakao에서 호출한 String address정보의 위도 경도를 갖고와서 찾는다
    private List<Direction> buildDirectionList(DocumentDto documentDto){
        // 약국 데이터 조회



        //거리계산 알고리즘을 이용하여, 고객와 약국 사이의 거리를 계산하고 sort
        return null;
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
