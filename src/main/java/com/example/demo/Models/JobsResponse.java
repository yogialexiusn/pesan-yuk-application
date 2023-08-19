package com.example.demo.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("JobsResponse")
public class JobsResponse implements Serializable {
    private String id;
    private String type;
    private String url;
    private String created_at;
    private String company;

    private String company_url;
    private  String location;
    private  String title;
    private  String description;
    private  String how_to_apply;
    private  String company_logo;
}
