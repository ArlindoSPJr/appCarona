package com.carona.AppDeCarona.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.carona.AppDeCarona.repository.AdminRepository;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if (adminRepository.existsByEmail(email)){
            return adminRepository.findByEmail(email);
        }
        throw new UsernameNotFoundException("Usuário não encontrado");
    }

}
