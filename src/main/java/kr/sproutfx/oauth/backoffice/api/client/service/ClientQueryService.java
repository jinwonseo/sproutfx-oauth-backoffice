package kr.sproutfx.oauth.backoffice.api.client.service;

import kr.sproutfx.oauth.backoffice.api.client.entity.Client;
import kr.sproutfx.oauth.backoffice.api.client.exception.ClientNotFoundException;
import kr.sproutfx.oauth.backoffice.api.client.repository.ClientQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ClientQueryService {
    private final ClientQueryRepository clientQueryRepository;

    public ClientQueryService(ClientQueryRepository clientQueryRepository) {
        this.clientQueryRepository = clientQueryRepository;
    }

    public List<Client> findAll() {
        return this.clientQueryRepository.findAllWithProject();
    }

    public Client findById(UUID id) {
        return this.clientQueryRepository.findByIdWithProject(id).orElseThrow(ClientNotFoundException::new);
    }

    public Client findByCodeWithProject(String code) {
        return this.clientQueryRepository.findByCodeWithProject(code).orElseThrow(ClientNotFoundException::new);
    }

    public Client findBySecretWithProject(String secret) {
        return this.clientQueryRepository.findBySecretWithProject(secret).orElseThrow(ClientNotFoundException::new);
    }
}
