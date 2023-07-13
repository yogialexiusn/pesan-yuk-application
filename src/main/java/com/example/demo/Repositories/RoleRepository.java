package com.example.demo.Repositories;

import com.example.demo.Models.Role;
import com.example.demo.Models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
