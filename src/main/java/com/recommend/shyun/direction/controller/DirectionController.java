package com.recommend.shyun.direction.controller;


import com.recommend.shyun.direction.entity.Direction;
import com.recommend.shyun.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DirectionController {

    private final DirectionService directionService;




    @GetMapping("/dir/{encodedId}")
    public String searchDirection(@PathVariable("encodedId") String encodedId){
       String result = directionService.findDirectionUrlById(encodedId);


        log.info("direction url: {}",  result);

        return "redirect:"+ result;


    }
}
