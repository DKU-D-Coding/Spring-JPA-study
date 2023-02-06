package hello.hellospring.controller;

import hello.hellospring.domain.Customer;
import hello.hellospring.service.CustomerService;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }



    @GetMapping("new")
    void join(@RequestParam("email") String em, @RequestParam("password") String ps ) {
        Customer cus = new Customer();
        cus.setemail(em);
        cus.setpassword(ps);
        customerService.join(cus);

    }



}