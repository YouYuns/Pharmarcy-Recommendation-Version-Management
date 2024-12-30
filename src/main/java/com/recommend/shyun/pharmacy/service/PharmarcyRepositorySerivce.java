package com.recommend.shyun.pharmacy.service;


import com.recommend.shyun.pharmacy.entity.Pharmacy;
import com.recommend.shyun.pharmacy.repository.PharmachRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmarcyRepositorySerivce {
    private final PharmachRepository pharmachRepository;


    @Transactional
    public void updateAddress(Long id, String address){
        Pharmacy pharmacy = pharmachRepository.findById(id).orElse(null);

        if(Objects.isNull(pharmacy)){
            log.error("[PharmarcyRepositorySerivce updateAddrress] not found id : {}", id);
            return;
        }

        pharmacy.changePharmachAddress(address);

    }

    @Transactional(readOnly = true)
    public List<Pharmacy> findAll(){
        return pharmachRepository.findAll();
    }
}
