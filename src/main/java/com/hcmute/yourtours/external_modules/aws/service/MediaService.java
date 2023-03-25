package com.hcmute.yourtours.external_modules.aws.service;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.external_modules.aws.config.AwsS3ClientConfig;
import com.hcmute.yourtours.external_modules.aws.config.AwsS3Properties;
import com.hcmute.yourtours.external_modules.aws.model.UploadMediaResponse;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Service
@Slf4j
public class MediaService implements IMediaService {
    private final AwsS3ClientConfig awsS3ClientConfig;
    private final AwsS3Properties awsS3Properties;
    private final SecureRandom random;

    public MediaService(AwsS3ClientConfig awsS3ClientConfig, AwsS3Properties awsS3Properties) {
        this.awsS3ClientConfig = awsS3ClientConfig;
        this.awsS3Properties = awsS3Properties;
        this.random = new SecureRandom();
    }


    @Override
    public UploadMediaResponse uploadFile(MultipartFile multipartFile) throws InvalidException {
        String fileUrl = "";
        try {
            if (multipartFile == null) {
                throw new InvalidException(YourToursErrorCode.FILE_ERROR_IS_NULL);
            }
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = awsS3Properties.getEndpointS3() + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
            return UploadMediaResponse.builder()
                    .createDate(LocalDateTime.now())
                    .previewUrl(fileUrl)
                    .build();
        } catch (InvalidException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidException(YourToursErrorCode.UPLOAD_FILE_ERROR);
        }

    }

    @Override
    public List<UploadMediaResponse> uploadFiles(List<MultipartFile> multipartFiles) throws InvalidException {
        if (multipartFiles == null) {
            return Collections.emptyList();
        }

        List<UploadMediaResponse> result = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            result.add(uploadFile(multipartFile));
        }

        return result;
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        awsS3ClientConfig.s3Client().putObject(new PutObjectRequest(awsS3Properties.getBucketName(), fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private String generateFileName(MultipartFile multiPart) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        byte[] bytes = String.valueOf(timestamp.getTime()).getBytes();
        random.setSeed(bytes);
        int randomNumber = random.nextInt(999);

        String name = timestamp.getTime() + "_" + randomNumber + "_";
        String originName = multiPart.getOriginalFilename();
        if (originName != null) {
            return name + originName.replace(" ", "_");
        }
        return name + "no-name";
    }


    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        if (file == null) {
            return null;
        }
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
            return convFile;
        }
    }
}
