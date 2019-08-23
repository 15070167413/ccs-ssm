package study.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import study.dto.Test;

import java.util.Date;
@RequestMapping("user")
@Controller
public class TestController {

    @ResponseBody
    @GetMapping("/{id:\\d+}")
    public void get(@PathVariable String id) {
            System.out.println(id);
    }


    @RequestMapping("gettest")
    @ResponseBody
    public Test getTest() {
        Test user = new Test();
        user.setUserName("mrbird");
        user.setBirthday(new Date());
        return user;
    }

    @Autowired
    ObjectMapper mapper;

    @RequestMapping("serialization")
    @ResponseBody
    public String serialization() {
        try {
            Test user = new Test();
            user.setUserName("mrbird");
            user.setBirthday(new Date());
            String str = mapper.writeValueAsString(user);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





    @RequestMapping("readjsonstring")
    @ResponseBody
    public String readJsonString() {
        try {
            String json = "{\"name\":\"mrbird\",\"age\":26}";
            JsonNode node = this.mapper.readTree(json);
            String name = node.get("name").asText();
            int age = node.get("age").asInt();
            return name + " " + age;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
