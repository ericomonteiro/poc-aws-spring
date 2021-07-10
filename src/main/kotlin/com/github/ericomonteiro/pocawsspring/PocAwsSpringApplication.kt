package com.github.ericomonteiro.pocawsspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PocAwsSpringApplication

fun main(args: Array<String>) {
	runApplication<PocAwsSpringApplication>(*args)
}
