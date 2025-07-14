package com.carona.AppDeCarona.controller.corrida;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.carona.AppDeCarona.controller.dto.corrida.AvaliarMotoristaDto;
import com.carona.AppDeCarona.controller.dto.corrida.CreateCorridaDto;
import com.carona.AppDeCarona.controller.dto.corrida.DetalharCorridaDto;
import com.carona.AppDeCarona.controller.dto.corrida.EntrarNaCorridaDTO;
import com.carona.AppDeCarona.service.CorridaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/corrida")
public class CorridaController {
    
    @Autowired
    private CorridaService corridaService;

    @PostMapping
    public ResponseEntity<DetalharCorridaDto> criarCorrida(@RequestBody @Valid CreateCorridaDto dto, UriComponentsBuilder uriBuilder){
        var corrida = corridaService.criarCorrida(dto);
        var uri = uriBuilder.path("/corrida/{id}").buildAndExpand(corrida.getCorridaId()).toUri();
        return ResponseEntity.created(uri).body(new DetalharCorridaDto(corrida));
    }

    @GetMapping
    public ResponseEntity<List<DetalharCorridaDto>> listarCorridas(){
        var corridas = corridaService.listarCorridas();
        return ResponseEntity.ok(corridas);
    }

    @PatchMapping("/cancelar/{corridaId}")
    public ResponseEntity cancelarCorrida(@PathVariable Long corridaId){
        corridaService.cancelarCorrida(corridaId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/iniciar/{corridaId}")
    public ResponseEntity iniciarCorrida(@PathVariable Long corridaId){
        corridaService.iniciarCorrida(corridaId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/finalizar/{corridaId}")
    public ResponseEntity finalizarCorrida(@PathVariable Long corridaId){
        corridaService.finalizarCorrida(corridaId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/avaliarMotorista/{corridaId}")
    public ResponseEntity avaliarMotorista(@PathVariable Long corridaId, @RequestBody AvaliarMotoristaDto dto){
        corridaService.avaliarMotorista(dto, corridaId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/entrarNaCorrida")
    public ResponseEntity<DetalharCorridaDto> entrarNaCorrida(@RequestBody EntrarNaCorridaDTO dto){
        var corrida = corridaService.entrarNaCorrida(dto.corridaId(), dto.passageiroId());
        return ResponseEntity.ok().body(new DetalharCorridaDto(corrida));
    }

}
