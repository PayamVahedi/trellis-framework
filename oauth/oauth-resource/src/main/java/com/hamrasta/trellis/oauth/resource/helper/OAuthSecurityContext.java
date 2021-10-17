package com.hamrasta.trellis.oauth.resource.helper;

import com.hamrasta.trellis.core.application.ApplicationContextProvider;
import com.hamrasta.trellis.core.log.Logger;
import com.hamrasta.trellis.oauth.resource.payload.Principle;
import com.hamrasta.trellis.oauth.resource.payload.Token;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.*;
import org.keycloak.adapters.rotation.AdapterTokenVerifier;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class OAuthSecurityContext {

    private static KeycloakDeployment keycloakDeployment;

    static KeycloakDeployment getKeycloakDeployment() {
        if (keycloakDeployment == null)
            keycloakDeployment = KeycloakDeploymentBuilder.build(ApplicationContextProvider.context.getBean(AdapterConfig.class));
        return keycloakDeployment;
    }

    public static Optional<Principle> findPrincipleByAccessToken(String token) {
        try {
            KeycloakDeployment deployment = getKeycloakDeployment();
            AccessToken accessToken = AdapterTokenVerifier.verifyToken(token, deployment);
            return Optional.of(new Principle(accessToken.getSubject(), accessToken.getEmail(), accessToken.getPreferredUsername(), accessToken.getGivenName(), accessToken.getFamilyName(), accessToken.getOtherClaims(), accessToken.getResourceAccess()));
        } catch (Exception e) {
            Logger.error("extractPrincipleByAccessToken", e.getMessage());
            return Optional.empty();
        }
    }

    public static Principle getPrinciple() {
        KeycloakPrincipal securityContext = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccessToken token = securityContext.getKeycloakSecurityContext().getToken();
        return token == null ? null : new Principle(token.getSubject(), token.getEmail(), token.getPreferredUsername(), token.getGivenName(), token.getFamilyName(), token.getOtherClaims(), token.getResourceAccess());
    }

    public static String getPrincipleId() {
        Principle principle = getPrinciple();
        return principle == null ? StringUtils.EMPTY : principle.getId();
    }

    public static Token getAccessToken() {
        KeycloakPrincipal securityContext = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccessToken token  = securityContext.getKeycloakSecurityContext().getToken();
        return new Token(token.getId(), securityContext.getKeycloakSecurityContext().getTokenString(), token.getScope(), Date.from(Instant.ofEpochSecond(token.getExp())), token.isExpired());
    }

    public static boolean hasRole(String role) {
        return hasAnyRole(role);
    }

    public static boolean hasAnyRole(String... roles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (roles == null || authentication == null)
            return false;
        for (String role : roles) {
            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equalsIgnoreCase("ROLE_" + role)))
                return true;
        }
        return false;
    }

}
