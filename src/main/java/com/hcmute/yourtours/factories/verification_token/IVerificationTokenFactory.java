package com.hcmute.yourtours.factories.verification_token;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.verification_token.VerificationTokenDetail;
import com.hcmute.yourtours.models.verification_token.VerificationTokenInfo;

import java.util.UUID;

public interface IVerificationTokenFactory extends IDataFactory<UUID, VerificationTokenInfo, VerificationTokenDetail> {
    VerificationTokenDetail getVerificationToken(String verificationToken) throws InvalidException;

    VerificationTokenDetail generateNewVerificationToken(String token) throws InvalidException;

    void createPasswordResetTokenForUser(UUID userId, String token);

    VerificationTokenDetail createVerificationTokenForUser(UUID userId) throws InvalidException;

    boolean validateVerificationCreateAccountToken(String token) throws InvalidException;
}
