package com.hcmute.yourtours.libs.configuration.security;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
public class DefaultUserDetail implements UserDetails {


    @JsonProperty("azp")
    public String issuedFor;
    @JsonProperty("resource_access")
    protected Map<String, Access> resourceAccess;
    @JsonProperty("scope")
    protected String scope;
    /**
     * Json web token id
     */
    @JsonProperty("jti")
    protected String id;
    protected Long exp;
    protected Long nbf;
    protected Long iat;
    @JsonProperty("iss")
    protected String issuer;
    @JsonProperty("sub")
    protected String subject;
    @JsonProperty("typ")
    protected String type;
    @JsonProperty("nonce")
    protected String nonce;

    protected Long auth_time;

    @JsonProperty("session_state")
    @JsonAlias("sid")
    protected String sessionState;

    @JsonProperty("at_hash")
    protected String accessTokenHash;

    @JsonProperty("c_hash")
    protected String codeHash;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("given_name")
    protected String givenName;

    @JsonProperty("family_name")
    protected String familyName;

    @JsonProperty("middle_name")
    protected String middleName;

    @JsonProperty("nickname")
    protected String nickName;

    @JsonProperty("preferred_username")
    protected String preferredUsername;

    @JsonProperty("profile")
    protected String profile;

    @JsonProperty("picture")
    protected String picture;

    @JsonProperty("website")
    protected String website;

    @JsonProperty("email")
    protected String email;

    @JsonProperty("email_verified")
    protected Boolean emailVerified;

    @JsonProperty("gender")
    protected String gender;

    @JsonProperty("birthdate")
    protected String birthdate;

    @JsonProperty("zoneinfo")
    protected String zoneInfo;

    @JsonProperty("locale")
    protected String locale;

    @JsonProperty("phone_number")
    protected String phoneNumber;

    @JsonProperty("phone_number_verified")
    protected Boolean phoneNumberVerified;

    @JsonProperty("address")
    protected AddressClaimSet address;

    @JsonProperty("updated_at")
    protected Long updatedAt;

    @JsonProperty("claims_locales")
    protected String claimsLocales;

    @JsonProperty("acr")
    protected String acr;

    @JsonProperty("s_hash")
    protected String stateHash;

    @JsonProperty("realm_access")
    protected Access realmAccess;

    protected Map<String, Object> otherClaims = new HashMap<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (Objects.nonNull(this.realmAccess)) {
            for (String role : realmAccess.getRoles()) {
                authorities.add(new DefaultGrantedAuthority("ROLE_" + role.toUpperCase()));
            }
        }

        if (Objects.nonNull(this.resourceAccess)) {
            for (Map.Entry<String, Access> stringAccessEntry : resourceAccess.entrySet()) {
                for (String role : realmAccess.getRoles()) {
                    authorities.add(new DefaultGrantedAuthority("ROLE_" + role.toUpperCase()));
                }
            }
        }
        if (authorities.isEmpty()) {
            DefaultGrantedAuthority gameGrantedAuthority = new DefaultGrantedAuthority("ROLE_ANONYMOUS");
            authorities = Collections.singletonList(gameGrantedAuthority);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return this.preferredUsername;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonAnyGetter
    public Map<String, Object> getOtherClaims() {
        return otherClaims;
    }

    @JsonAnySetter
    public void setOtherClaims(String name, Object value) {
        otherClaims.put(name, value);
    }

    public static class Access implements Serializable {
        @JsonProperty("roles")
        protected Set<String> roles;
        @JsonProperty("verify_caller")
        protected Boolean verifyCaller;

        public Access() {
        }

        public Access clone() {
            Access access = new Access();
            access.verifyCaller = verifyCaller;
            if (roles != null) {
                access.roles = new HashSet<>();
                access.roles.addAll(roles);
            }
            return access;
        }

        public Set<String> getRoles() {
            return roles;
        }

        public Access roles(Set<String> roles) {
            this.roles = roles;
            return this;
        }

        @JsonIgnore
        public boolean isUserInRole(String role) {
            if (roles == null) return false;
            return roles.contains(role);
        }

        public Access addRole(String role) {
            if (roles == null) roles = new HashSet<>();
            roles.add(role);
            return this;
        }

        public Boolean getVerifyCaller() {
            return verifyCaller;
        }

        public Access verifyCaller(Boolean required) {
            this.verifyCaller = required;
            return this;
        }
    }

    @Data
    public static class AddressClaimSet {
        @JsonProperty("formatted")
        protected String formattedAddress;

        @JsonProperty("street_address")
        protected String streetAddress;

        @JsonProperty("locality")
        protected String locality;

        @JsonProperty("region")
        protected String region;

        @JsonProperty("postal_code")
        protected String postalCode;

        @JsonProperty("country")
        protected String country;
    }
}
