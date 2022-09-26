package com.hcmute.yourtours.libs.factory;

import com.hcmute.yourtours.libs.exceptions.InvalidException;

public interface IPersistDataFactory<IF, DT extends IF, ET> {
    ET createConvertToEntity(DT detail) throws InvalidException;

    void updateConvertToEntity(ET entity, DT detail) throws InvalidException;

    DT convertToDetail(ET entity) throws InvalidException;

    IF convertToInfo(ET entity) throws InvalidException;
}
