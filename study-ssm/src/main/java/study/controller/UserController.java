package study.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.dto.PageInfo;
import study.dto.User;
import study.util.ExcelUtil;
import study.service.UserService;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private  UserService userService;

    @Autowired
    private  ExcelUtil excelUtil;

    public static ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @RequestMapping("/user")
    @ResponseBody
    private List<User> findUsers(){
        String str=null;
        logger.error("---------------这是我自定义的error错误-------开始---");
        List<User> list=userService.findUsers();
        logger.error("---------------这是我自定义的error错误-------结束---");

        return list;
    }

    @RequestMapping(value = "/page",method=RequestMethod.GET)
    public String showPage(Model model,@RequestParam(value = "pageNumberStr", required = false, defaultValue = "1") int pageNumberStr){

            int pageSize = 2;
         int pageNumber = 1;
           if(pageNumberStr!=1){
               pageNumber = pageNumberStr;
          }
        if(pageNumberStr==0){
            pageNumber = 1;
        }
        long cou=userService.selCount();
        int count=(int)cou;
        long tatol=count % pageSize == 0 ? count / pageSize : count / pageSize + 1;

        if(pageNumberStr==tatol+1){

            pageNumber--;
        }
         PageInfo pi=new PageInfo();
        userService.showPage (pi,pageSize,pageNumber);


            model.addAttribute("list",pi.getList());
            model.addAttribute("pageNumber",pageNumber);
        model.addAttribute("tatol",tatol);
            return "page";
    }





    @RequestMapping("/list")
    public String findUsers( Model model){
        List<User> list=userService.findUsers();

        model.addAttribute("list",list);
        return "user1";
    }


    @RequestMapping("/del/{name}")
    public String del(@PathVariable String name){
        userService.del(name);
        return "redirect:/list";
    }

    @RequestMapping("/edit/{name}")
    public String edit(@PathVariable String name, Model model){
        User user = userService.findUserByName(name);
        model.addAttribute("user",user);
        return "edit";
    }

    @RequestMapping("toAdd")
    public String toAdd(){
        return "add";
    }

    @RequestMapping("/add")
    public String edit(User user)throws Exception{
        if(user==null)throw new Exception();
        userService.add(user);
        return "redirect:/list";
    }


    @RequestMapping("/editdo")
    public String editdo(User user){
        userService.edit(user);
        return "redirect:/list";
    }








}