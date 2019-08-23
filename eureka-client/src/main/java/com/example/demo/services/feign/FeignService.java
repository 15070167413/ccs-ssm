package com.example.demo.services.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//@FeignClient("ssm")
public interface FeignService {

    @RequestMapping("/list")
    public String findUsers( Model model);
}
