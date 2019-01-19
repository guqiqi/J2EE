package nju.yummy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class YummyApplication {

    @RequestMapping("/hello")
    String index(){
        System.out.println("hello");
        return "Hello Spring Boot";
    }

    public static void main(String[] args) {
        SpringApplication.run(YummyApplication.class, args);
    }
}

