package com.github.ericomonteiro.pocawsspring.config.aws

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AWSClients (
    private val awsCredentials: AWSCredentials
) {

    @Bean
    fun s3Client(): AmazonS3 = AmazonS3ClientBuilder
        .standard()
        .withRegion(Regions.US_EAST_1)
        .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
        .build()

}