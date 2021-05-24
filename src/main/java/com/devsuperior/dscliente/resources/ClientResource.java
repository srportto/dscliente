package com.devsuperior.dscliente.resources;

import com.devsuperior.dscliente.dto.ClientDTO;
import com.devsuperior.dscliente.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {
    @Autowired
    private ClientService service;

    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ){
        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction), orderBy );
        Page<ClientDTO> pageClient = service.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(pageClient);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
        ClientDTO clientDTO = service.findById(id);
        return ResponseEntity.ok().body(clientDTO);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> inserirCliente(@RequestBody ClientDTO clientDTO) {
        clientDTO = service.insert(clientDTO);

        // preenchedo o header(location) de retorno com a uri do recurso criado
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(clientDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(clientDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> atualizarCliente(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        clientDTO = service.update(id, clientDTO);

        return ResponseEntity.ok().body(clientDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

}
