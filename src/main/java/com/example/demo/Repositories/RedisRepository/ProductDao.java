package com.example.demo.Repositories.RedisRepository;

import com.example.demo.Entity.Product;

import com.example.demo.Models.JobsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    public static final String HASH_KEY = "Product";
    @Autowired
    private RedisTemplate template;

    public Product save(Product product){
        template.opsForHash().put(HASH_KEY,product.getId(),product);
        return product;
    }

    public JobsResponse save(JobsResponse jobsResponses){
        template.opsForHash().put(HASH_KEY,jobsResponses.getId(),jobsResponses);
        return jobsResponses;
    }

    public JobsResponse findJobResponseById(String id){
        return (JobsResponse) template.opsForHash().get(HASH_KEY,id);
    }


    public List<Product> findAll(){
        return template.opsForHash().values(HASH_KEY);
    }

    public Product findProductById(int id){
        return (Product) template.opsForHash().get(HASH_KEY,id);
    }


    public String deleteProduct(int id){
        template.opsForHash().delete(HASH_KEY,id);
        return "product removed !!";
    }
}