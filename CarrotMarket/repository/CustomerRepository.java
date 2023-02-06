package hello.hellospring.repository;

import hello.hellospring.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer save(Customer product);
    Optional<Customer> findByCname(String name);
    Customer findByCid(Long pid);
    List<Customer> findAll();
}
