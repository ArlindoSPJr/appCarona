package com.carona.AppDeCarona.controller.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.carona.AppDeCarona.controller.dto.Usuario.CriarUsuarioDto;
import com.carona.AppDeCarona.controller.dto.Usuario.DetalharUsuarioDto;
import com.carona.AppDeCarona.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<DetalharUsuarioDto> criarCliente(@RequestBody CriarUsuarioDto dto, UriComponentsBuilder uriBuilder){
        var usuario = usuarioService.criarUsuario(dto);
        var uri = uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.getUsuarioId()).toUri();
        return ResponseEntity.created(uri).body(new DetalharUsuarioDto(usuario));

    }

    @GetMapping
    public ResponseEntity<Page<DetalharUsuarioDto>> buscarClientes(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        var page = usuarioService.getUsuarios(paginacao);
        return ResponseEntity.ok(page);
    }

}
