package org.wsd.app.domain.listener;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.wsd.app.domain.AbstractAuditableEntity;

public class AuditEntityListener {
    @PrePersist
    public void prePersist(Object object) {
        // Logic to run before the entity is persisted
        //System.out.println("Before persisting: " + object);
    }

    @PostPersist
    public void postPersist(Object object) {
        // Logic to run after the entity is persisted
        //System.out.println("After persisting: " + object);
    }

    @PreUpdate
    public void preUpdate(AbstractAuditableEntity abstractAuditableEntity) {
        // Logic to run after the entity is updated
        //System.out.println("Before updating: " + object);

    }

    @PostUpdate
    public void postUpdate(Object object) {
        // Logic to run after the entity is updated
        //System.out.println("After updating: " + object);
    }

    @PreRemove
    public void preRemove(Object object) {
        // Logic to run before the entity is removed
        //System.out.println("Before removing: " + object);
    }

    @PostRemove
    public void postRemove(Object object) {
        // Logic to run after the entity is removed
        //System.out.println("After removing: " + object);
    }

    @PostLoad
    public void postLoad(Object object) {
        // Logic to run after the entity is loaded
        //System.out.println("After loading: " + object);
    }
}