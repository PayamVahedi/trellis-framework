package com.hamrasta.trellis.data.core.payload;

import com.hamrasta.trellis.context.payload.DiscoveryRule;
import com.hamrasta.trellis.context.rule.Rule;

import java.util.HashSet;
import java.util.Set;

public class DiscoveryEntityRule {
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

    public DiscoveryEntityRule() {
    }

    public DiscoveryEntityRule(boolean isSuccess, Set<Rule<?>> rules) {
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
