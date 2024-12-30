package com.recommend.shyun.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {


    @JsonProperty("address_name")
    private String addressName;

    //위도
    @JsonProperty("y")
    private double latitude;

    //경도
    @JsonProperty("x")
    private double longitude;
}
