package com.github.ericomonteiro.pocawsspring.s3.controller

import com.amazonaws.AmazonServiceException
import com.amazonaws.SdkClientException
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.Bucket
import com.amazonaws.services.s3.model.CreateBucketRequest
import com.amazonaws.services.s3.model.ObjectListing
import com.amazonaws.services.s3.model.Region
import com.github.ericomonteiro.pocawsspring.config.error.ErrorResponse
import com.github.ericomonteiro.pocawsspring.s3.controller.dto.CreateBucketDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream

@RestController
@RequestMapping("/s3/bucket")
class BucketController(
    private val s3Client: AmazonS3
) {

    var logger: Logger = LoggerFactory.getLogger(BucketController::class.java)

    @GetMapping
    fun listBuckets(): List<Bucket>? = s3Client.listBuckets()

    @GetMapping("/{bucketName}")
    fun listObjectFromBucket(@PathVariable bucketName: String): ObjectListing = s3Client.listObjects(bucketName)

    @PostMapping
    fun createBucket(@RequestBody createBucketDto: CreateBucketDto): ResponseEntity<Any> {
        return try {
            s3Client.createBucket(createBucketDto.name)
            ResponseEntity.status(HttpStatus.CREATED.value()).build()
        } catch (ex: SdkClientException) {
            logger.error("Error creating a bucket with name=${createBucketDto.name}", ex)
            ResponseEntity.badRequest().body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.ApiError("0000", ex.message ?: "")))
        } catch (ex: AmazonServiceException) {
            logger.error("Error creating a bucket with name=${createBucketDto.name}", ex)
            ResponseEntity.badRequest().body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.ApiError("0000", ex.message ?: "")))
        }
    }

    @DeleteMapping("/{bucketName}")
    fun deleteBucket(@PathVariable bucketName: String): ResponseEntity<Any> {
        return try {
            s3Client.deleteBucket(bucketName)
            ResponseEntity.status(HttpStatus.OK).build()
        } catch (ex: SdkClientException) {
            logger.error("Error trying to delete the bucket with name=$bucketName", ex)
            ResponseEntity.badRequest().body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.ApiError("0000", ex.message ?: "")))
        } catch (ex: AmazonServiceException) {
            logger.error("Error trying to delete the bucket with name=$bucketName", ex)
            ResponseEntity.badRequest().body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.ApiError("0000", ex.message ?: "")))
        }
    }

    @PostMapping("/{bucketName}")
    fun sendFile(@PathVariable bucketName: String,
                 @RequestParam("file") multipartFile: MultipartFile
    ): ResponseEntity<Any> {
        val file = File(multipartFile.originalFilename)
        val fos = FileOutputStream(file)
        fos.write(multipartFile.bytes)
        fos.close()
        return try {
            s3Client.putObject(bucketName, multipartFile.originalFilename, file)
            file.delete()
            ResponseEntity.status(HttpStatus.CREATED.value()).build()
        } catch (ex: SdkClientException) {
            logger.error("Error putting an object with name=${multipartFile.name}", ex)
            ResponseEntity.badRequest().body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.ApiError("0000", ex.message ?: "")))
        } catch (ex: AmazonServiceException) {
            logger.error("Error putting an object with name=${multipartFile.name}", ex)
            ResponseEntity.badRequest().body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.ApiError("0000", ex.message ?: "")))
        }
    }

    @DeleteMapping("/{bucketName}/{objectKey}")
    fun deleteFile(@PathVariable bucketName: String, @PathVariable objectKey: String): ResponseEntity<Any> {
        return try {
            s3Client.deleteObject(bucketName, objectKey)
            ResponseEntity.status(HttpStatus.OK.value()).build()
        } catch (ex: SdkClientException) {
            logger.error("Error trying to delete object in bucket=$bucketName with key=$objectKey", ex)
            ResponseEntity.badRequest().body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.ApiError("0000", ex.message ?: "")))
        } catch (ex: AmazonServiceException) {
            logger.error("Error trying to delete object in bucket=$bucketName with key=$objectKey", ex)
            ResponseEntity.badRequest().body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.ApiError("0000", ex.message ?: "")))
        }
    }

}