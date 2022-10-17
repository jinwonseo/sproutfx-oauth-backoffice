package kr.sproutfx.oauth.backoffice.api.client.dto.response;

import kr.sproutfx.oauth.backoffice.api.client.entity.Client;
import kr.sproutfx.oauth.backoffice.api.project.dto.response.ProjectResponse;
import kr.sproutfx.oauth.backoffice.common.response.base.BaseResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientResponse extends BaseResponse {
    private String code;
    private String name;
    private String secret;
    private String accessTokenSecret;
    private Long accessTokenValidityInSeconds;
    private String status;
    private String description;

    private ProjectResponse project;

    public ClientResponse(Client client) {
        super(client.getId());
        this.code = client.getCode();
        this.name = client.getName();
        this.secret = client.getSecret();
        this.accessTokenSecret = client.getAccessTokenSecret();
        this.accessTokenValidityInSeconds = client.getAccessTokenValidityInSeconds();
        this.status = client.getStatus().toString();
        this.description = client.getDescription();

        this.project = new ProjectResponse(client.getProject());
    }
}
