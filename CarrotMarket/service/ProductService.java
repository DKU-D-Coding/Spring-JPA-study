package hello.hellospring.service;

import hello.hellospring.domain.Product;
import hello.hellospring.repository.ProductRepository;


import java.util.List;
import java.util.Optional;

//@service
public class ProductService {
    private final ProductRepository productRepository;
    //@Autowired
    public ProductService(ProductRepository productRepository) { //의존관계 추가
        this.productRepository = productRepository;
    }

    public List<Product> findProducts() { //전체 회원 조회
        return productRepository.findAll();
    }

    public void HeartClick(Long pid) {
        Product pd = productRepository.findByPid(pid);
        pd.setHeart(pd.getHeart()+1);
        productRepository.save(pd);
    }

    public void HeartUnClick(Long pid) {
        Product pd = productRepository.findByPid(pid);
        pd.setHeart(pd.getHeart()-1);
        productRepository.save(pd);
    }
}