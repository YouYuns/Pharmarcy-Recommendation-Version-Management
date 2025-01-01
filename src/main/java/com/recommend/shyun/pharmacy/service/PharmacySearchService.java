package com.recommend.shyun.pharmacy.service;


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

    private final PharmarcyRepositorySerivce pharmarcyRepositorySerivce;

    public List<PharmacyDto> searchPharmacyDtoList (){

        //redis


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
