package com.devsuperior.dscliente.services;

import com.devsuperior.dscliente.dto.ClientDTO;
import com.devsuperior.dscliente.entities.Client;
import com.devsuperior.dscliente.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true) //para não travar o recurso no banco nas operacoes de somente leitura
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        Page<Client> pageClient = clientRepository.findAll(pageRequest);
        return pageClient.map(client -> new ClientDTO(client));
    }

}
