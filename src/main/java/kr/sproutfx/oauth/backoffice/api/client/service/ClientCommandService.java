package kr.sproutfx.oauth.backoffice.api.client.service;

import kr.sproutfx.oauth.backoffice.api.client.entity.Client;
import kr.sproutfx.oauth.backoffice.api.client.enumeration.ClientStatus;
import kr.sproutfx.oauth.backoffice.api.client.exception.ClientNotFoundException;
import kr.sproutfx.oauth.backoffice.api.client.repository.ClientRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import java.util.UUID;

@Service
@Transactional
public class ClientCommandService {
    private final ClientRepository clientRepository;

    public ClientCommandService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public UUID create(String name, String description) {

        Client persistenceClient = this.clientRepository.save(Client.builder().code(UUID.randomUUID().toString().replace("-", StringUtils.EMPTY)).name(name).secret(Base64Utils.encodeToUrlSafeString(RandomStringUtils.randomAlphanumeric(32).getBytes())).status(ClientStatus.PENDING_APPROVAL).accessTokenSecret(RandomStringUtils.randomAlphanumeric(96)).accessTokenValidityInSeconds(7200L).description(description).build());

        return persistenceClient.getId();
    }

    public void update(UUID id, String name, Long accessTokenValidityInSeconds, String description) {

        Client persistenceClient = this.clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);

        persistenceClient.setName(name);
        persistenceClient.setAccessTokenValidityInSeconds(accessTokenValidityInSeconds);
        persistenceClient.setDescription(description);
    }

    public void updateStatus(UUID id, ClientStatus clientStatus) {

        Client persistenceClient = this.clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);

        persistenceClient.setStatus(clientStatus);
    }

    public void deleteById(UUID id) {

        Client persistenceClient = this.clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);

        this.clientRepository.delete(persistenceClient);
    }
}
