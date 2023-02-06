package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String str, Model model) {
        //@RequestParam(value = "name", required = false
        model.addAttribute("data", str);
        return "hello";
    }

    @GetMapping("hello-api-str")
    @ResponseBody
    public String helloString(@RequestParam("name") String str) {
        return str;
    }

    @GetMapping("hello-api-json")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String str) {
        Hello hello = new Hello();
        hello.setName(str);
        return hello;
    }
    static class Hello {
        private String name;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        //단축키 alt + insert
    }
}
