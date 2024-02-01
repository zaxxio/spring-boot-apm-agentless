package org.wsd.app.domain;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.wsd.app.domain.listener.AuditEntityListener;

@Getter
@Setter
@ToString
@Entity
@Table(name = "roles")
@RequiredArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditEntityListener.class)
public class RoleEntity extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID roleId;
    private String name;
    @ManyToOne(targetEntity = UserEntity.class, cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private UserEntity users;
    @Version
    private int version;
}