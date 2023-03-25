package com.hcmute.yourtours.controllers.media;


import com.hcmute.yourtours.external_modules.aws.model.UploadMediaResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;


public interface IMediaController {

    @PostMapping(value = "/public/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload file hình ảnh lên s3")
    ResponseEntity<BaseResponse<UploadMediaResponse>> putObjectFile(@RequestBody MultipartFile file);


    @PostMapping(value = "/public/upload/lists", consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload files hình ảnh lên s3")
    ResponseEntity<BaseResponse<List<UploadMediaResponse>>> putListObjectFile(@RequestBody List<MultipartFile> files);
}
