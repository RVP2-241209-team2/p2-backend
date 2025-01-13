package com.revature.shoply.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Parameters {

    private final String secret;

    private final String awsAccessKeyId;

    private final String awsSecretAccessKey;

    private final String awsRegion;

    private final String s3BucketName;

    @Autowired
    public Parameters(
            @Qualifier("jwtSecret") String secret,
            @Qualifier("awsAccessKeyId") String awsAccessKeyId,
            @Qualifier("awsSecretAccessKey") String awsSecretAccessKey,
            @Qualifier("awsRegion") String awsRegion,
            @Qualifier("s3BucketName") String s3BucketName) {
        this.secret = secret;
        this.awsAccessKeyId = awsAccessKeyId;
        this.awsSecretAccessKey = awsSecretAccessKey;
        this.awsRegion = awsRegion;
        this.s3BucketName = s3BucketName;
    }

    public String getSecret() {
        return secret;
    }

    public String getAwsAccessKeyId() {
        return awsAccessKeyId;
    }

    public String getAwsSecretAccessKey() {
        return awsSecretAccessKey;
    }

    public String getAwsRegion() {
        return awsRegion;
    }

    public String getS3BucketName() {
        return s3BucketName;
    }
}
