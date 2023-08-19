package com.example.demo.Controllers;

import com.example.demo.Models.CodeResponse;
import com.example.demo.Models.JobsResponse;
import com.example.demo.Services.IJobImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class JobsRestApis {

    @Autowired
    IJobImpl job;

    @GetMapping("/listJobs")
    @PreAuthorize("hasRole('ADMIN')")
    JobsResponse[] getListJobs(){
        return job.getListJobs();
    }

    @GetMapping("/{jobDetail}")
    @PreAuthorize("hasRole('ADMIN')")
    JobsResponse getJob(@PathVariable String jobDetail){
        return job.getJob(jobDetail);
    }

    @GetMapping("/code")
    @PreAuthorize("hasRole('ADMIN')")
    CodeResponse getCode(){
        return job.getCode();
    }

    @GetMapping("/code123")
    @PreAuthorize("hasRole('ADMIN')")
    List<Map<String, Object>> getCodeWithHashMap(List<Map<String, String>> inputList){
        return job.getCodeWithHashMap(inputList);
    }
}
