package com.example.demo.controller;

import com.example.demo.services.feign.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FeignController {
    @Autowired
    FeignService feignService;

    @RequestMapping("/list")
    public String findUsers( Model model){
        return feignService.findUsers(model);
    };

}
