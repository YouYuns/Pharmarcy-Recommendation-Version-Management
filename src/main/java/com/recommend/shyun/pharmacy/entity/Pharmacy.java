package com.recommend.shyun.pharmacy.entity;

import com.recommend.shyun.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;


@Entity(name = "pharmacy")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pharmacy extends BaseTimeEntity {

    @Id
    //DB가 PK값을 대신 생성해주는 전략
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String pharmacyName;
    private String pharmacyAddress;

    private double latitude;
    private double longitude;


    public void changePharmachAddress(String addres){
        this.pharmacyAddress = addres;
    }
}
