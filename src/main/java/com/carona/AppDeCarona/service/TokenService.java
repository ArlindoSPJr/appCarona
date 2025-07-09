package com.carona.AppDeCarona.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.carona.AppDeCarona.entity.Admin;
import com.carona.AppDeCarona.entity.Usuario;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Object user) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            String login;
            Long id;
            String role;

            if (user instanceof Admin admin) {
                login = admin.getEmail();
                id = admin.getAdminId();
                role = "ROLE_ADMIN";  // Adiciona prefixo ROLE_
            } else if (user instanceof Usuario usuario){
                login = usuario.getEmail();
                id = usuario.getUsuarioId();
                role = "ROLE_USUARIO";
            }
            else {
                throw new IllegalArgumentException("Tipo de usuário desconhecido");
            }

            return JWT.create()
                    .withIssuer("AppCarona")
                    .withSubject(login)
                    .withClaim("id", id.toString())
                    .withClaim("roles", List.of(role)) // Agora é um array
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token jwt", exception);
        }
    }


    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("AppCarona")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Erro ao verificar token jwt", exception);
        }
    }

    public List<String> getRoles(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("AppCarona")
                    .build()
                    .verify(tokenJWT)
                    .getClaim("roles").asList(String.class); // Extrai como lista
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Erro ao verificar token jwt", exception);
        }
    }


    public Instant dataExpiracao(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
