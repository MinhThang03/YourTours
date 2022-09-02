package com.hcmute.yourtours.libs.factory;

public interface IPersistDataFactory<IF, DT extends IF, ET> {
    ET createConvertToEntity(DT detail);

    void updateConvertToEntity(ET entity, DT detail);

    DT convertToDetail(ET entity);

    IF convertToInfo(ET entity);
}
