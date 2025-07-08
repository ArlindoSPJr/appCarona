package com.carona.AppDeCarona.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.carona.AppDeCarona.entity.Admin;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    UserDetails findByEmail(String email);

    boolean existsByEmail(String email);

}
