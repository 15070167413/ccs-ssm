package study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;



@EnableCaching
@SpringBootApplication(scanBasePackages = "study",exclude = {DataSourceAutoConfiguration.class})
@MapperScan("study.mapper")
public class StudyApplication {
    public static void main(String[] args){
        SpringApplication.run(StudyApplication.class, args);
    }
}