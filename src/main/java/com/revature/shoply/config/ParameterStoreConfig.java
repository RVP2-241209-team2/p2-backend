package com.revature.shoply.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;

@Configuration
public class ParameterStoreConfig {

    @Bean
    public SsmClient ssmClient() {
        return SsmClient.builder()
                .region(Region.US_EAST_2)
                .build();
    }

    @Bean
    public String jwtSecret(SsmClient ssmClient) {
        GetParameterRequest request = GetParameterRequest.builder()
                .name("/prod/shoply/jwt.secret")
                .withDecryption(true)
                .build();

        return ssmClient.getParameter(request)
                .parameter()
                .value();
    }

    @Bean
    public String awsAccessKeyId(SsmClient ssmClient) {
        GetParameterRequest request = GetParameterRequest.builder()
                .name("/prod/shoply/aws.access.key.id")
                .withDecryption(true)
                .build();

        return ssmClient.getParameter(request)
                .parameter()
                .value();
    }

    @Bean
    public String awsSecretAccessKey(SsmClient ssmClient) {
        GetParameterRequest request = GetParameterRequest.builder()
                .name("/prod/shoply/aws.secret.access.key")
                .withDecryption(true)
                .build();

        return ssmClient.getParameter(request)
                .parameter()
                .value();
    }

    @Bean
    public String awsRegion(SsmClient ssmClient) {
        GetParameterRequest request = GetParameterRequest.builder()
                .name("/prod/shoply/aws.region")
                .withDecryption(true)
                .build();

        return ssmClient.getParameter(request)
                .parameter()
                .value();
    }

    @Bean
    public String s3BucketName(SsmClient ssmClient) {
        GetParameterRequest request = GetParameterRequest.builder()
                .name("/prod/shoply/s3.bucket.name")
                .withDecryption(true)
                .build();

        return ssmClient.getParameter(request)
                .parameter()
                .value();
    }

}
