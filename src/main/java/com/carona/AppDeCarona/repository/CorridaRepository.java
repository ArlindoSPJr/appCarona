package com.carona.AppDeCarona.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carona.AppDeCarona.entity.Corrida;

public interface CorridaRepository extends JpaRepository<Corrida, Long> {
    
}
