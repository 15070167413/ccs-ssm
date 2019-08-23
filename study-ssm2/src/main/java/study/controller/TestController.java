package study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @RequestMapping("/test")
    @ResponseBody
    public Map<String,Object> test(){
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("key","study");
        return result;
    }


    @RequestMapping("/test2")
    @ResponseBody
    public Map<String,Object> test2(){
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("key","study");
        return result;
    }
}
