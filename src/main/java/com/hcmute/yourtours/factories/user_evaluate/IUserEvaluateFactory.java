package com.hcmute.yourtours.factories.user_evaluate;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateDetail;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateInfo;

import java.util.UUID;

public interface IUserEvaluateFactory extends IDataFactory<UUID, UserEvaluateInfo, UserEvaluateDetail> {
    Double getAverageRateOfHome(UUID homeId);
}
