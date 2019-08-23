package study.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import study.dto.User;


@Mapper
public interface UserMapper {
    User findUserByName(String name);

}
