package com.github.ericomonteiro.pocawsspring.config.aws

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.SystemPropertiesCredentialsProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AWSConfig {

    @Bean
    fun awsCredentials(): AWSCredentials = SystemPropertiesCredentialsProvider().credentials

}