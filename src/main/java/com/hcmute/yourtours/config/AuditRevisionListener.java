package com.hcmute.yourtours.config;

import com.hcmute.yourtours.libs.configuration.IConfigFactory;
import org.hibernate.envers.RevisionListener;

public class AuditRevisionListener implements RevisionListener {
    private final IConfigFactory iConfigFactory;

    public AuditRevisionListener(IConfigFactory iConfigFactory) {
        this.iConfigFactory = iConfigFactory;
    }

    @Override
    public void newRevision(Object revisionEntity) {
        AuditRevisionEntity auditRevisionEntity = (AuditRevisionEntity) revisionEntity;
        if (iConfigFactory.auditorAware().getCurrentAuditor().isPresent()) {
            auditRevisionEntity.setUserId(iConfigFactory.auditorAware().getCurrentAuditor().get().toString());
        } else {
            auditRevisionEntity.setUserId("anonymous");
        }
    }
}
