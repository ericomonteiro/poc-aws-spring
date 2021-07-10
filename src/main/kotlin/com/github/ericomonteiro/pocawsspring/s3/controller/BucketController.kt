package com.github.ericomonteiro.pocawsspring.s3.controller

import com.amazonaws.AmazonServiceException
import com.amazonaws.SdkClientException
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.Bucket
import com.amazonaws.services.s3.model.CreateBucketRequest
import com.amazonaws.services.s3.model.Region
import com.github.ericomonteiro.pocawsspring.s3.controller.dto.CreateBucketDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/s3/bucket")
class BucketController(
    private val s3Client: AmazonS3
) {

    var logger: Logger = LoggerFactory.getLogger(BucketController::class.java)

    @GetMapping
    fun listBuckets(): List<Bucket>? = s3Client.listBuckets()

    @PostMapping
    fun createBucket(@RequestBody createBucketDto: CreateBucketDto): ResponseEntity<Any> {
        val createBucketRequest = CreateBucketRequest(createBucketDto.name, Region.US_East_2)
        return try {
            s3Client.createBucket(createBucketRequest)
            ResponseEntity.status(HttpStatus.CREATED.value()).build()
        } catch (ex: SdkClientException) {
            logger.error("Error creating a bucket with name=${createBucketDto.name}", ex)
            ResponseEntity.badRequest().body(ex)
        } catch (ex: AmazonServiceException) {
            logger.error("Error creating a bucket with name=${createBucketDto.name}", ex)
            ResponseEntity.badRequest().body(ex)
        }
    }
}