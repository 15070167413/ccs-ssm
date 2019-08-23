import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import study.dto.User;
        import study.service.UserService;

        import java.util.List;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestJunit.class)
public class TestJunit {

    @Autowired
    private UserService userService;
    @Test
    public void test1() throws Exception {
        User user=userService.findUserByName("张三");
        System.out.println(user);
    }

}
