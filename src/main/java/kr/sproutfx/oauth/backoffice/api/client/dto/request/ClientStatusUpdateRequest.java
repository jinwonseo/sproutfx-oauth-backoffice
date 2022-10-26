package kr.sproutfx.oauth.backoffice.api.client.dto.request;

import kr.sproutfx.oauth.backoffice.api.client.enumeration.ClientStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ClientStatusUpdateRequest {
    @NotBlank
    private ClientStatus clientStatus;
}
