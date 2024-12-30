package com.recommend.shyun.pharmacy.repository

import com.recommend.shyun.AbstractIntegrationContainerBaseTest
import com.recommend.shyun.api.pharmacy.entity.Pharmacy
import com.recommend.shyun.api.pharmacy.repository.PharmachRepository
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest{

    @Autowired
    private PharmachRepository pharmachRepository;
    //테스트 메서드 실행되기전에 실행되는 메서드
    def setup(){
        pharmachRepository.deleteAll()
    }

    def "PharmacyRepository save" (){
        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
                                .pharmacyAddress(address)
                                .pharmacyName(name)
                                .latitude(latitude)
                                .longitude(longitude)
                                .build()

        when:
        def result = pharmachRepository.save(pharmacy)

        then:
        result.getPharmacyAddress() == address
        result.getPharmacyName() == name
        result.getLatitude() == latitude
        result.getLongitude() == longitude


    }

    def "Pharmacy saveAll"(){
        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        pharmachRepository.saveAll(Arrays.asList(pharmacy))
        def result = pharmachRepository.findAll()

        then:
        result.size() == 1
    }
}
