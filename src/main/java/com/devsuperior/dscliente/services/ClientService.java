package com.devsuperior.dscliente.services;

import com.devsuperior.dscliente.dto.ClientDTO;
import com.devsuperior.dscliente.entities.Client;
import com.devsuperior.dscliente.repositories.ClientRepository;
import com.devsuperior.dscliente.services.exceptions.DatabaseException;
import com.devsuperior.dscliente.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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

    @Transactional
    public ClientDTO insert(ClientDTO clientDTO) {
        Client entity = new Client();

        // invocacao do metodo que transforma dto em entity aos moldes do BD para posterior save/update
        copyDtoToEntity(clientDTO, entity);

        entity = clientRepository.save(entity);

        return new ClientDTO(entity);
    }


    @Transactional
    public ClientDTO update(Long id, ClientDTO clientDTO) {

        try {
            Client entity = clientRepository.getOne(id); //getOne atribui o id ao usuario sem ir ao banco, so vai no banco no save

            // invocacao do metodo que transforma dto em entity aos moldes do BD para posterior save/update
            copyDtoToEntity(clientDTO, entity);

            entity = clientRepository.save(entity);

            return new ClientDTO(entity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id nao encontrado " + id);

        }
    }

    public void delete(Long id) {
        try {
            clientRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id nao encontrado " + id);
        }
        catch (DataIntegrityViolationException e){ //exception de integridade referencial/tentar deletar algo em uso por outros
            throw new DatabaseException("Violacao de integridade");
        }
    }


    //-----------------------------------------------
    // Metodo utilitario para insert e update 
    //------------------------------------------------
    private  void copyDtoToEntity(ClientDTO dto, Client entity){
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }
}
