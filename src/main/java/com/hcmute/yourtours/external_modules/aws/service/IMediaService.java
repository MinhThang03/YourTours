package com.hcmute.yourtours.external_modules.aws.service;

import com.hcmute.yourtours.external_modules.aws.model.UploadMediaResponse;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMediaService {
    UploadMediaResponse uploadFile(MultipartFile multipartFile) throws InvalidException;

    List<UploadMediaResponse> uploadFiles(List<MultipartFile> multipartFiles) throws InvalidException;
}
