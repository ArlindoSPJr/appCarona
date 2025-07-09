package com.carona.AppDeCarona.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.carona.AppDeCarona.controller.dto.Usuario.CriarUsuarioDto;
import com.carona.AppDeCarona.controller.dto.Usuario.DetalharUsuarioDto;
import com.carona.AppDeCarona.entity.Usuario;
import com.carona.AppDeCarona.repository.UsuarioRepository;
import org.springframework.data.domain.Page;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario criarUsuario(CriarUsuarioDto dto){
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }
        var usuario = new Usuario(dto);
        usuario.setPassword(passwordEncoder.encode(dto.password()));
        usuarioRepository.save(usuario);
        return usuario;
    }

    public Page<DetalharUsuarioDto> getUsuarios(Pageable paginacao) {
        return usuarioRepository.findAll(paginacao).map(DetalharUsuarioDto::new);
    }

    
}
