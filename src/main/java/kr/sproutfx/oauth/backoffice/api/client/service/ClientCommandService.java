package kr.sproutfx.oauth.backoffice.api.client.service;

import kr.sproutfx.oauth.backoffice.api.client.entity.Client;
import kr.sproutfx.oauth.backoffice.api.client.enumeration.ClientStatus;
import kr.sproutfx.oauth.backoffice.api.client.enumeration.ClientType;
import kr.sproutfx.oauth.backoffice.api.client.exception.ClientNotFoundException;
import kr.sproutfx.oauth.backoffice.api.client.repository.ClientCommandRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import java.util.UUID;

@Service
@Transactional
public class ClientCommandService {
    private final ClientCommandRepository clientCommandRepository;

    public ClientCommandService(ClientCommandRepository clientCommandRepository) {
        this.clientCommandRepository = clientCommandRepository;
    }

    public UUID create(String name, String description) {

        Client persistenceClient = this.clientCommandRepository.save(
            Client.builder()
                .code(UUID.randomUUID().toString().replace("-", StringUtils.EMPTY))
                .name(name)
                .type(ClientType.APPLICATION_SERVER)
                .secret(Base64Utils.encodeToUrlSafeString(RandomStringUtils.randomAlphanumeric(32).getBytes()))
                .status(ClientStatus.PENDING_APPROVAL)
                .accessTokenSecret(RandomStringUtils.randomAlphanumeric(96))
                .accessTokenValidityInSeconds(7200L)
                .description(description)
                .build());

        return persistenceClient.getId();
    }

    public void update(UUID id, String name, String description) {

        Client persistenceClient = this.clientCommandRepository.findById(id).orElseThrow(ClientNotFoundException::new);

        persistenceClient.update(name, description);
    }

    public void updateStatus(UUID id, ClientStatus clientStatus) {

        Client persistenceClient = this.clientCommandRepository.findById(id).orElseThrow(ClientNotFoundException::new);

        persistenceClient.updateStatus(clientStatus);
    }

    public void deleteById(UUID id) {

        Client persistenceClient = this.clientCommandRepository.findById(id).orElseThrow(ClientNotFoundException::new);

        this.clientCommandRepository.delete(persistenceClient);
    }
}
