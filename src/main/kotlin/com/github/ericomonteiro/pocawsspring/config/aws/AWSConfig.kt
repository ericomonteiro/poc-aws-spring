package com.github.ericomonteiro.pocawsspring.config.aws

import com.amazonaws.auth.BasicAWSCredentials
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AWSConfig {

    @Bean
    fun awsCredentials() = BasicAWSCredentials(
        "foo",
        "bar"
    )

}