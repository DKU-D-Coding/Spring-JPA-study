package hello.hellospring.repository;

import hello.hellospring.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product product);
    Optional<Product> findByPname(String name);
    Product findByPid(Long pid);
    List<Product> findAll();
}
