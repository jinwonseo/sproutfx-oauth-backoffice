package kr.sproutfx.oauth.backoffice.api.client.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.sproutfx.oauth.backoffice.api.client.enumeration.ClientStatus;
import kr.sproutfx.oauth.backoffice.api.client.enumeration.ClientType;
import kr.sproutfx.oauth.backoffice.api.project.entity.Project;
import kr.sproutfx.oauth.backoffice.configuration.jpa.entity.JpaBaseEntity;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.*;
import org.hibernate.envers.Audited;
import org.springframework.util.Base64Utils;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "clients")
@DynamicInsert
@DynamicUpdate
@Audited
@SQLDelete(sql = "UPDATE clients SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Client extends JpaBaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private ClientType type;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String code;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String name;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String secret;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String accessTokenSecret;

    @Column(nullable = false, columnDefinition = "integer")
    private Long accessTokenValidityInSeconds;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private ClientStatus status;

    @Column(columnDefinition = "varchar(255)")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;


    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void updateType(ClientType type) {
        this.type = type;
    }

    public void updateCode() {
        this.code = UUID.randomUUID().toString().replace("-", StringUtils.EMPTY);
    }

    public void updateSecret() {
        this.secret = Base64Utils.encodeToUrlSafeString(RandomStringUtils.randomAlphanumeric(32).getBytes());
    }

    public void updateStatus(ClientStatus status) {
        this.status = status;
    }

    public void updateAccessTokenSecret() {
        this.accessTokenSecret = RandomStringUtils.randomAlphanumeric(128);
    }

    public void updateAccessTokenValidityInSeconds(Long accessTokenValidityInSeconds) {
        this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
    }
}
