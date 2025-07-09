package com.carona.AppDeCarona.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.carona.AppDeCarona.repository.AdminRepository;
import com.carona.AppDeCarona.repository.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if (adminRepository.existsByEmail(email)){
            return adminRepository.findByEmail(email);
        }
        if (usuarioRepository.existsByEmail(email)) {
            return usuarioRepository.findByEmail(email);
        }
        throw new UsernameNotFoundException("Usuário não encontrado");
    }

}
