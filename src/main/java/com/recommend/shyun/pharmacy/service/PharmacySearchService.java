package com.recommend.shyun.pharmacy.service;


import com.recommend.shyun.pharmacy.cache.PharmacyRedisTemplateService;
import com.recommend.shyun.pharmacy.dto.PharmacyDto;
import com.recommend.shyun.pharmacy.entity.Pharmacy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacySearchService {

    private final PharmacyRepositorySerivce pharmarcyRepositorySerivce;

    private final PharmacyRedisTemplateService pharmachRedisTemplateService;

    public List<PharmacyDto> searchPharmacyDtoList (){

        //redis
        List<PharmacyDto> pharmacyDtoList = pharmachRedisTemplateService.findAll();
        
        //redis가 비어있지않으면 가져오기
        if(!pharmacyDtoList.isEmpty()) {
            log.info("redis findAll success !!");
            return pharmacyDtoList;
        }


        //문제있어서 비어있으면 db에서 값가져오기
        //db
      return  pharmarcyRepositorySerivce.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }


    private PharmacyDto toDto(Pharmacy pharmacy){
        return PharmacyDto.builder()
                .id(pharmacy.getId())
                .pharmacyAddress(pharmacy.getPharmacyAddress())
                .pharmacyName(pharmacy.getPharmacyName())
                .latitude(pharmacy.getLatitude())
                .longitude(pharmacy.getLongitude())
                .build();
    }

}
