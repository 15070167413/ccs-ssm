package study.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import study.dto.PageInfo;
import study.dto.User;
import study.mapper.UserMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UserService {
    @SuppressWarnings("All")
    @Autowired
    private UserMapper userMapper;

    public User findUserByName(String name) {
        return this.userMapper.findUserByName(name);
    }

    public List<User> findUsers() {
        return this.userMapper.findUsers();
    }

    public void edit(User user) {
        userMapper.edit(user);
    }

    public void del(String name) {
        userMapper.del(name);
    }

    public void add(User user) throws Exception {
        if (user == null) {
            throw new NullPointerException();
        }
        userMapper.add(user);
    }

    public long selCount(){
        long count = userMapper.selCount();
        return count;
    }


    public void showPage(PageInfo pi, int pageSize, int pageNumber) {

        pi.setPageNumber(pageNumber);
        pi.setPageSize(pageSize);
        Map<String, Object> map = new HashMap<>();
        int pageStart = pageSize * (pageNumber - 1);
        List<User> list = userMapper.selByPage(pageStart, pageSize);
        pi.setList(list);
        //总条数
        long count = userMapper.selCount();
        pi.setTotal(count);
        pi.setTotal(count % pageSize == 0 ? count / pageSize : count / pageSize + 1);

    }
}
