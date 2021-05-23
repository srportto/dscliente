package com.devsuperior.dscliente.services;

import com.devsuperior.dscliente.dto.ClientDTO;
import com.devsuperior.dscliente.entities.Client;
import com.devsuperior.dscliente.repositories.ClientRepository;
import com.devsuperior.dscliente.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true) //para não travar o recurso no banco nas operacoes de somente leitura
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        Page<Client> pageClient = clientRepository.findAll(pageRequest);
        return pageClient.map(client -> new ClientDTO(client));
    }

    @Transactional(readOnly = true) //para não travar o recurso no banco nas operacoes de somente leitura
    public ClientDTO findById(Long id) {

        //Captura o objeto de Client de um optional e se não encontrar lança exceção
        Optional<Client> optionalClient = clientRepository.findById(id);
        Client client = optionalClient.orElseThrow(() -> new ResourceNotFoundException("Recurso nao encontrado"));

        // chama o return ja criando o objeto de retorno "clientDTO"  com o construtor que recebe um objeto Client
        return new ClientDTO(client);
    }
}
