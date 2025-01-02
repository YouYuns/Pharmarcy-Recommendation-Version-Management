package com.recommend.shyun.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentDto {

    @JsonProperty("place_name")
    private String placeName;

    @JsonProperty("address_name")
    private String addressName;

    //위도
    @JsonProperty("y")
    private double latitude;

    //경도
    @JsonProperty("x")
    private double longitude;

    @JsonProperty("distance")
    private double distance;
}
