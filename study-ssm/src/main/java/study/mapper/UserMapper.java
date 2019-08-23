package study.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import study.dto.User;
import java.util.List;


@Mapper
public interface UserMapper {

    User findUserByName(String name);


    List<User> findUsers();

    void del(String name);

    void edit(@Param(value="user")User user);

    void add(@Param(value="user")User user);
    List<User> selByPage(@Param(value="pageStart")int pageStart, @Param(value="pageSize")int pageSize);
    long selCount();
}
