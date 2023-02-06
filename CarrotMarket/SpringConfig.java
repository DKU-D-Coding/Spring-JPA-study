package hello.hellospring;
import hello.hellospring.domain.Customer;
import hello.hellospring.repository.*;
import hello.hellospring.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringConfig {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    public SpringConfig(ProductRepository productRepository, CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }
    //인터페이스만 만들면 스프링이 구현체를 만들어 bean에

    @Bean
    public ProductService productService() {
        return new ProductService(productRepository);
    }

    @Bean
    public CustomerService customerService() {
        return new CustomerService(customerRepository);
    }
}
