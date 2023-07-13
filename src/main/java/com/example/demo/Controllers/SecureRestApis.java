package com.example.demo.Controllers;

import com.example.demo.Models.JobsResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class SecureRestApis {

    @GetMapping("/test/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess(){
        return "user end points";
    }

    @GetMapping("/test/pm")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
    public String pmAccess(){
        return "pm end points";
    }

    @GetMapping("/test/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess(){
        return "admin content that contains password";
    }

}
