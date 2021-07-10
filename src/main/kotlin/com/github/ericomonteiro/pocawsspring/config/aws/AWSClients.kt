package com.github.ericomonteiro.pocawsspring.config.aws

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AWSClients (
    private val awsCredentials: BasicAWSCredentials
) {

    val endpointOverride = AwsClientBuilder.EndpointConfiguration("http://localhost:4566", Regions.US_EAST_1.name)

    @Bean
    fun s3Client(): AmazonS3 = AmazonS3ClientBuilder
        .standard()
        .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
        .withEndpointConfiguration(endpointOverride)
        .build()

}