package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class CacheController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @PostConstruct
    public void addData() {
        Customer customer1 = new Customer();
        customer1.setCustomerName("cust-1");
        customer1.setCity("chennai");
        Customer customer2 = new Customer();
        customer2.setCustomerName("cust-2");
        customer2.setCity("hyderabad");

        customerRepository.save(customer1);
        customerRepository.save(customer2);

    }

    @PostMapping("addCustomer")
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.saveData(customer);
    }

    @GetMapping("getCustomer/{id}")
    public Customer getCustomer(@PathVariable("id") int id)
    {
        return customerService.getData(id);
    }
}

