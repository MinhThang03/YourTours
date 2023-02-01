package com.hcmute.yourtours.keycloak;


import com.hcmute.yourtours.libs.exceptions.InvalidException;
import org.keycloak.representations.AccessTokenResponse;

import java.util.List;

public interface IKeycloakService {
    String createUser(String userName, String firstName, String lastName, String email, String password) throws InvalidException;

    boolean deleteUser(String userId);

    AccessTokenResponse getJwt(String userName, String password) throws InvalidException;

    AccessTokenResponse getRefreshToken(String refreshToken) throws InvalidException;

    void invalidateToken(String token) throws InvalidException;

    void addUserClientRoles(String userId, List<String> roles) throws InvalidException;

    void addUserRealmRoles(String userId, List<String> roles) throws InvalidException;

    void setPassword(String userId, String password) throws InvalidException;

    boolean introspectToken(String token);

    boolean isEmailVerify(String userId);

    void activeUserEmail(String userId);

    void logout() throws InvalidException;

    Boolean validUserPassword(String username, String password) throws InvalidException;

}
