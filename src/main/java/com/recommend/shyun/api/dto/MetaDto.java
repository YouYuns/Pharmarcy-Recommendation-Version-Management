package com.recommend.shyun.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MetaDto {

    //total_count값으로 들어오는 json값을 totalCount로 Mapping
    @JsonProperty("total_count")
    private Integer totalCount;
}
