package eureka.feign;


import eureka.dto.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
public class FeignBack implements FeignService{

    @RequestMapping("/user")
    @ResponseBody
    public List<User> findUsers(){

        return null;
    }

}