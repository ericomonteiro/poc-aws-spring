package com.github.ericomonteiro.pocawsspring.s3.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/s3/bucket")
class BucketController {

    @GetMapping
    fun listBuckets() {

    }

}