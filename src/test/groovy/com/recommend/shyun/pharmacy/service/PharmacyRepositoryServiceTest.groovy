package com.recommend.shyun.pharmacy.service

import com.recommend.shyun.AbstractIntegrationContainerBaseTest
import com.recommend.shyun.pharmacy.repository.PharmachRepository
import org.springframework.beans.factory.annotation.Autowired


class PharmacyRepositoryServiceTest extends  AbstractIntegrationContainerBaseTest{

    @Autowired
    private PharmarcyRepositorySerivce pharmarcyRepositorySerivce;

    @Autowired
    private PharmachRepository pharmachRepository;


    def setup(){
        pharmachRepository.deleteAll()
    }

    def "PharmacyRepository update - dirty checking success"(){
        given:
        String inputAddress = "서울 특별시 성북구 종암동"
        String modifiedAddress = "서울 광진구 구의동"
        String name = "은혜 약국"

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(inputAddress)
                .pharmacyName(name)
                .build()
        when:
        def entity = pharmacyRepository.save(pharmacy)
        pharmacyRepositoryService.updateAddress(entity.getId(), modifiedAddress)

        def result = pharmachRepository.findAll()

        then:
        result.get(0).getPharmacyAddress() == modifiedAddress

    }
}
