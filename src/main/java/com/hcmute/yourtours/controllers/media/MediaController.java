package com.hcmute.yourtours.controllers.media;


import com.hcmute.yourtours.aws.model.UploadMediaResponse;
import com.hcmute.yourtours.aws.service.MediaService;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/media")
@Tag(name = "MEDIA API: AWS S3")
@Transactional
@Slf4j
public class MediaController implements IMediaController {
    protected final IResponseFactory iResponseFactory;
    private final MediaService mediaService;

    public MediaController(
            MediaService mediaService,
            IResponseFactory iResponseFactory) {
        this.mediaService = mediaService;
        this.iResponseFactory = iResponseFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<UploadMediaResponse>> putObjectFile(MultipartFile file) {
        try {
            UploadMediaResponse response = mediaService.uploadFile(file);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e.getErrorCode());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<List<UploadMediaResponse>>> putListObjectFile(List<MultipartFile> files) {
        try {
            List<UploadMediaResponse> response = mediaService.uploadFiles(files);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e.getErrorCode());
        }
    }

}
