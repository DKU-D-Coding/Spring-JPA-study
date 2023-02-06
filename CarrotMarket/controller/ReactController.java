package hello.hellospring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ReactController {

    @GetMapping("hello")
    public List<String> hello() {
        return Arrays.asList("강동원","여진구","송중기");
    }
}