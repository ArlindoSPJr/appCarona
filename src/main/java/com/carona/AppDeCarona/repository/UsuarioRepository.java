package com.carona.AppDeCarona.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.carona.AppDeCarona.entity.Usuario;
import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    boolean existsByEmail(String email);

    UserDetails findByEmail(String email);

    Usuario findByNome(String nome);

    Usuario findByUsuarioId(Long usuarioId);

    
    
}
