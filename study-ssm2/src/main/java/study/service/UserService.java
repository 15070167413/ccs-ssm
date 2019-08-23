package study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.dto.User;
import study.mapper.UserMapper;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findUserByName(String name){
      return this.userMapper.findUserByName(name);
    }
}
