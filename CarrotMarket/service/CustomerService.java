package hello.hellospring.service;

import hello.hellospring.domain.Customer;
import hello.hellospring.repository.CustomerRepository;


import java.util.List;
import java.util.Optional;

//@service
public class CustomerService {
    private final CustomerRepository customerRepository;
    //@Autowired
    public CustomerService(CustomerRepository customerRepository) { //의존관계 추가
        this.customerRepository = customerRepository;
    }

    public List<Customer> findCustomers() { //전체 회원 조회
        return customerRepository.findAll();
    }

    public Long join(Customer customer) { // 회원가입
        validateDuplicateMember(customer); //중복 회원 체크
        customerRepository.save(customer);
        return customer.getcid();
    }
    private void validateDuplicateMember(Customer customer) {
       customerRepository.findByCname(customer.getcname())
                .ifPresent(m -> { //optinal 값이 null이 아니면
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
}