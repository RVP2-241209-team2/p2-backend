package com.revature.shoply.product.config;

import com.revature.shoply.config.Parameters;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {

//    @Value("${aws.access.key.id}")
    private String accessKey;

//    @Value("${aws.secret.access.key}")
    private String secretKey;

//    @Value("${aws.s3.region}")
    private String region;

    private Parameters params;

    public S3Config(Parameters params) {
        this.params = params;
    }

    @PostConstruct
    public void init() {
        this.accessKey = params.getAwsAccessKeyId();
        this.secretKey = params.getAwsSecretAccessKey();
        this.region = params.getAwsRegion();

    }

    @Bean
    public AmazonS3 s3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }
}
