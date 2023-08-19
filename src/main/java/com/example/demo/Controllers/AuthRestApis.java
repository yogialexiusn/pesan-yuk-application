package com.example.demo.Controllers;

import com.example.demo.Config.JwtTokenProvider;
import com.example.demo.Models.*;
import com.example.demo.Repositories.RoleRepository;
import com.example.demo.Repositories.UserRepository;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestApis {

    private static final Logger logger = LoggerFactory.getLogger(AuthRestApis.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signin")
    public ResponseEntity authenticatUser(@Valid @RequestBody LoginForm loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        logger.info("cek {}, ",JSONValue.toJSONString(authentication));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        logger.info("cek1 {}, ",JSONValue.toJSONString(jwt));
        return ResponseEntity.ok(new JWTResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity registerUser(@Valid @RequestBody SignUpForm signupRequest) {
       // APAKAH ADA USERNAME SEPERTI ITU
        if(userRepository.existsByUsername(signupRequest.getUsername())){
            return new ResponseEntity("Gagal Bos : UserName udah ada sebelumnya", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return new ResponseEntity("Gagal Bos : Email udah ada sebelumnya", HttpStatus.BAD_REQUEST);
        }

        User user = new User(signupRequest.getName(),
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));
        logger.info("cek {}, " ,JSONValue.toJSONString(user));

        Set strRoles = signupRequest.getRole();

        Set roles = new HashSet();

        // AMBIL SEMUA DARI DALAMNYA
        strRoles.forEach(role ->{
            switch(role.toString()){
                case "admin":
                    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Gagal! admin role not be found"));
                    roles.add(adminRole);
                    break;
                case "pm":
                    Role pmRole = roleRepository.findByName(RoleName.ROLE_PM)
                            .orElseThrow(() -> new RuntimeException("Gagal! pm role not be found"));
                    roles.add(pmRole);
                    break;
                default:
                    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Gagal! user role not be found"));
                    roles.add(userRole);
                    break;
            }
        });

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok().body("Berhasil , user telah didaftar!");
    }

}
