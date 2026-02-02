package org.ltl;

import org.microjvm.di.annotation.RequestScope;
import org.microjvm.di.annotation.Service;

import jakarta.annotation.PostConstruct;

import java.util.UUID;

@Service
@RequestScope
public class RequestTracker {
    private final String requestId = UUID.randomUUID().toString();
    
    @PostConstruct
    public void init() {
        System.out.println("[RequestScope] New RequestTracker created: " + requestId);
    }
    
    public String getRequestId() {
        return requestId;
    }
}
