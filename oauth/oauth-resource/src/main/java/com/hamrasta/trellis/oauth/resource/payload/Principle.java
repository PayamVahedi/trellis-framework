package com.hamrasta.trellis.oauth.resource.payload;

import org.keycloak.representations.AccessToken;

import java.util.Map;

public class Principle {
    private String id;

    private String email;

    private String username;

    private String firstName;

    private String lastName;

    private Map<String, Object> attributes;

    Map<String, AccessToken.Access> resourceAccess;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, AccessToken.Access> getResourceAccess() {
        return resourceAccess;
    }

    public void setResourceAccess(Map<String, AccessToken.Access> resourceAccess) {
        this.resourceAccess = resourceAccess;
    }

    public Principle() {
    }

    public Principle(String id, String email, String username, String firstName, String lastName, Map<String, Object> attributes, Map<String, AccessToken.Access> resourceAccess) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.attributes = attributes;
        this.resourceAccess = resourceAccess;
    }

    public static Principle of() {
        return new Principle();
    }

    public static Principle of(String id, String email, String username, String firstName, String lastName, Map<String, Object> attributes, Map<String, AccessToken.Access> resourceAccess) {
        return new Principle(id, email, username, firstName, lastName, attributes, resourceAccess);
    }

    @Override
    public String toString() {
        return "Principle{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
