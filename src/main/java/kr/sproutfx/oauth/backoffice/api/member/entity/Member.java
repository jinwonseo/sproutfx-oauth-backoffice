package kr.sproutfx.oauth.backoffice.api.member.entity;

import kr.sproutfx.oauth.backoffice.api.member.enumeration.MemberStatus;
import kr.sproutfx.oauth.backoffice.api.member.exception.MemberPasswordNotMatchesException;
import kr.sproutfx.oauth.backoffice.configuration.jpa.entity.JpaBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "members")
@DynamicInsert
@DynamicUpdate
@Audited
@SQLDelete(sql = "UPDATE members SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Member extends JpaBaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varchar(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String email;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String name;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String password;

    @Column(nullable = false, columnDefinition = "timestamp")
    private LocalDateTime passwordExpired;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Column(columnDefinition = "varchar(255)")
    private String description;

    public void update(String email, String name, String description) {
        this.email = email;
        this.name = name;
        this.description = description;
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String currentPassword, String newPassword) {
        if (!passwordEncoder.matches(currentPassword, this.password)) throw new MemberPasswordNotMatchesException();

        this.password = passwordEncoder.encode(newPassword);
        this.passwordExpired = LocalDateTime.now().plusDays(90);
    }

    public void updateStatus(MemberStatus status) {
        this.status = status;
    }
}
