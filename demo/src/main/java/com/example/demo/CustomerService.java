package com.example.demo;

import org.redisson.api.RMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CustomerService {
    @Autowired
    private  CustomerRepository customerRepository;


    private static final Logger logger= LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    private  RedissonClient redissonClient;

        public Customer saveData(Customer data) {
            // Store data in MySQL
            Customer customer = customerRepository.save(data);

            // Store data in Redis
            RMap<Integer, Customer> redisMap = redissonClient.getMap("yourDataMap");
            customer= redisMap.put(data.getId() , data);

            return customer;
        }

    public Customer getData(int id) {
        // Attempt to retrieve data from Redis
        RMap<Integer, Customer> redisMap = redissonClient.getMap("yourDataMap");
        Customer dataFromRedis = redisMap.get(id);

        if (dataFromRedis != null) {

            logger.info("this data is coming from redis");
            return dataFromRedis; // Data found in Redis, return it
        } else {
            // Data not found in Redis, attempt to retrieve from MySQL

            logger.info("this data is coming from database");
            return customerRepository.findById(id).orElse(null);
        }
    }

        // Other methods to access data from both MySQL and Redis
    }

