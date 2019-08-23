package study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.dto.User;
import study.mapper.UserMapper;
import study.service.UserService;

import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {

    @Autowired
    private  UserService userService;

    @RequestMapping("/user")
    public Map<String,Object> findUserByName(String name){
        User user=userService.findUserByName("陈春生");
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("ccs",user);
        return result;
    }

}
