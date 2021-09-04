package ar.com.arbitrium.chain

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChainApplication

fun main(args: Array<String>) {
	runApplication<ChainApplication>(*args)
}
