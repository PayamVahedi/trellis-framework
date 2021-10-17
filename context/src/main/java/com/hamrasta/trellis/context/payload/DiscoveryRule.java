package com.hamrasta.trellis.context.payload;

import com.hamrasta.trellis.context.rule.Rule;
import com.hamrasta.trellis.core.payload.IPayload;

import java.util.HashSet;
import java.util.Set;

public class DiscoveryRule implements IPayload {
    private boolean isSuccess;

    private Set<Rule<?>> rules;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Set<Rule<?>> getRules() {
        return rules;
    }

    public void setRules(Set<Rule<?>> rules) {
        this.rules = rules;
    }

    public DiscoveryRule() {
    }

    public DiscoveryRule(boolean isSuccess, Set<Rule<?>> rules) {
        this.isSuccess = isSuccess;
        this.rules = rules;
    }

    public static DiscoveryRule of() {
        return of(false);
    }

    public static DiscoveryRule of(boolean isSuccess) {
        return of(isSuccess, new HashSet<>());
    }

    public static DiscoveryRule of(Set<Rule<?>> rules) {
        return of(true, rules);
    }

    public static DiscoveryRule of(boolean isSuccess, Set<Rule<?>> rules) {
        return new DiscoveryRule(isSuccess, rules);
    }
}
