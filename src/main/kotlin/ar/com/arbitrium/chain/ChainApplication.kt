package ar.com.arbitrium.chain

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ChainApplication

fun main(args: Array<String>) {
	runApplication<ChainApplication>(*args)
}