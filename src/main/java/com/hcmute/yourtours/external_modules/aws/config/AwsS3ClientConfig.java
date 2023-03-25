package com.hcmute.yourtours.external_modules.aws.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AwsS3Properties.class)
public class AwsS3ClientConfig {
    private final AwsS3Properties awsS3Properties;

    public AwsS3ClientConfig(AwsS3Properties awsS3Properties) {
        this.awsS3Properties = awsS3Properties;
    }

    public AmazonS3 s3Client() {

        AWSCredentials credentials = new BasicAWSCredentials(
                awsS3Properties.getAccessKey(),
                awsS3Properties.getSecretKey()
        );

        return AmazonS3ClientBuilder
                .standard()
                .withRegion(awsS3Properties.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
