package hello.hellospring.controller;

import hello.hellospring.domain.Product;
import hello.hellospring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("products")
    public List<Product> list() {
        List<Product> products = productService.findProducts();
        return products;
    }

    @GetMapping("heartclick")
    void heartclick(@RequestParam("pid") Long pid) {
        productService.HeartClick(pid);
    }

    @GetMapping("heartunclick")
    void heartunclick(@RequestParam("pid") Long pid) {
        productService.HeartUnClick(pid);
    }


}
