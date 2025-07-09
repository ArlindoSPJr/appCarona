package com.carona.AppDeCarona.controller.dto.Usuario;

import com.carona.AppDeCarona.entity.Usuario;

public record DetalharUsuarioDto(String nome,
                                 String email,
                                 String password,
                                 int avaliacao) {
    public DetalharUsuarioDto(Usuario usuario){
        this(usuario.getNome(), usuario.getEmail(), usuario.getPassword(), usuario.getAvaliacao());
    }                               
    
}
