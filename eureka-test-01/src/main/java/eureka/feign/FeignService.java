package eureka.feign;

import eureka.dto.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(value = "myssm" ,fallback = FeignBack.class)
public interface FeignService {
    @RequestMapping("/user")
    @ResponseBody
    List<User> findUsers();

}
