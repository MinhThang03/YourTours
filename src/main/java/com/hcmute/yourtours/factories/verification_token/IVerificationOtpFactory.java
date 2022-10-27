package com.hcmute.yourtours.factories.verification_token;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.verification_token.VerificationOtpDetail;
import com.hcmute.yourtours.models.verification_token.VerificationOtpInfo;

import java.util.UUID;

public interface IVerificationOtpFactory extends IDataFactory<UUID, VerificationOtpInfo, VerificationOtpDetail> {
    VerificationOtpDetail getVerificationToken(String verificationToken) throws InvalidException;

    VerificationOtpDetail generateNewVerificationToken(String token) throws InvalidException;

    VerificationOtpDetail createVerificationOtpForUser(UUID userId) throws InvalidException;

    VerificationOtpDetail createVerificationForgotPasswordOtp(UUID userId) throws InvalidException;

    VerificationOtpDetail verifyCreateAccountOtp(String token) throws InvalidException;
}
