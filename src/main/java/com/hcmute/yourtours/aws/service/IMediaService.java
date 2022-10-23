package com.hcmute.yourtours.aws.service;

import com.hcmute.yourtours.aws.model.UploadMediaResponse;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import org.springframework.web.multipart.MultipartFile;

public interface IMediaService {
    UploadMediaResponse uploadFile(MultipartFile multipartFile) throws InvalidException;
}
