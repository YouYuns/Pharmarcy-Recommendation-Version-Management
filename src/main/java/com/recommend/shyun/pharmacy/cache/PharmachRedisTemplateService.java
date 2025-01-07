package com.recommend.shyun.pharmacy.cache;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recommend.shyun.pharmacy.dto.PharmacyDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmachRedisTemplateService {

    //KEY값을 지정
    private static final String CACHE_KEY = "PHARMACY";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    //HashOperation이라는 자료구조 ( key subkey value )
    private HashOperations<String, String, String> hashOperations;

    //생성자 주입이 이루어지고나서 restTemplate에 제공하는 hash자료구조를 이용
    @PostConstruct
    public void init(){
        this.hashOperations = redisTemplate.opsForHash();
    }


    //저장
    public void save(PharmacyDto pharmacyDto) {
        if(Objects.isNull(pharmacyDto) || Objects.isNull(pharmacyDto.getId())) {
            log.error("Required Values must not be null");
            return;
        }

        try {
            //KEY값  : PHARMACY , SUBKEY값 : DB에 저장된 ID값 , VALUE값 : JSON타입으로 변환된 PHARMACYDTO
            hashOperations.put(CACHE_KEY,
                    pharmacyDto.getId().toString(),
                    serializePharmacyDto(pharmacyDto));
            log.info("[PharmacyRedisTemplateService save success] id: {}", pharmacyDto.getId());
        } catch (Exception e) {
            log.error("[PharmacyRedisTemplateService save error] {}", e.getMessage());
        }
    }


    public List<PharmacyDto> findAll() {

        try {
            List<PharmacyDto> list = new ArrayList<>();

            //entries로 redis에 캐쉬된 모든 value값을 가져온다.
            for (String value : hashOperations.entries(CACHE_KEY).values()) {
                PharmacyDto pharmacyDto = deserializePharmacyDto(value);
                list.add(pharmacyDto);
            }
            return list;

        } catch (Exception e) {
            log.error("[PharmacyRedisTemplateService findAll error]: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public void delete(Long id) {
        hashOperations.delete(CACHE_KEY, String.valueOf(id));
        log.info("[PharmacyRedisTemplateService delete]: {} ", id);
    }


    //약국dto를 받아서 json타입으로 변환
    private String serializePharmacyDto(PharmacyDto pharmacyDto) throws JsonProcessingException {
        //String으로 변환해주는데 그 형태가 json
        return objectMapper.writeValueAsString(pharmacyDto);
    }


    //반대부분 dto로 변환
    private PharmacyDto deserializePharmacyDto(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, PharmacyDto.class);
    }


}
