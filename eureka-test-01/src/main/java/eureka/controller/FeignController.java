package eureka.controller;

import eureka.dto.User;
import eureka.feign.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class FeignController {
    @Qualifier("feignBack")
    @Autowired
    FeignService feignService;

    @ResponseBody
    @RequestMapping("/feign")
    public  List<User> hello(){
        List<User> list= feignService.findUsers();
        return list;
        }
}
