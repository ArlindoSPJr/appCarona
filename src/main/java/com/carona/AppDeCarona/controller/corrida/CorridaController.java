package com.carona.AppDeCarona.controller.corrida;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.carona.AppDeCarona.controller.dto.corrida.CreateCorridaDto;
import com.carona.AppDeCarona.controller.dto.corrida.DetalharCorridaDto;
import com.carona.AppDeCarona.service.CorridaService;

@RestController
@RequestMapping("/corrida")
public class CorridaController {
    
    @Autowired
    private CorridaService corridaService;

    @PostMapping
    public ResponseEntity<DetalharCorridaDto> criarCorrida(@RequestBody CreateCorridaDto dto, UriComponentsBuilder uriBuilder){
        var corrida = corridaService.criarCorrida(dto);
        var uri = uriBuilder.path("/corrida/{id}").buildAndExpand(corrida.getCorridaId()).toUri();
        return ResponseEntity.created(uri).body(new DetalharCorridaDto(corrida));
    }

    @GetMapping
    public ResponseEntity<List<DetalharCorridaDto>> listarCorridas(){
        var corridas = corridaService.listarCorridas();
        return ResponseEntity.ok(corridas);
    }

}
