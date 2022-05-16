package kr.sproutfx.oauth.backoffice.api.client.service;

import kr.sproutfx.oauth.backoffice.api.client.entity.Client;
import kr.sproutfx.oauth.backoffice.api.client.exception.ClientNotFoundException;
import kr.sproutfx.oauth.backoffice.api.client.repository.ClientRepository;
import kr.sproutfx.oauth.backoffice.api.client.repository.specification.ClientSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ClientQueryService {
    private final ClientRepository clientRepository;

    public ClientQueryService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return this.clientRepository.findAll();
    }

    public Client findById(UUID id) {
        return this.clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
    }

    public Client findByCode(String code) {
        return this.clientRepository.findOne(ClientSpecification.equalCode(code)).orElseThrow(ClientNotFoundException::new);
    }

    public Client findBySecret(String secret) {
        return this.clientRepository.findOne(ClientSpecification.equalSecret(secret)).orElseThrow(ClientNotFoundException::new);
    }
}
