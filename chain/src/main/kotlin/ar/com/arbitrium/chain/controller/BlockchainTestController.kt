package ar.com.arbitrium.chain.controller

import ar.com.arbitrium.chain.model.BlockChain
import ar.com.arbitrium.chain.model.Bloque
import ar.com.arbitrium.chain.model.Transaccion
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bc")
class BlockchainTestController(val bc: BlockChain = BlockChain()) {

    @GetMapping
    fun get1(): BlockChain = bc

    @PostMapping
    fun post1(@RequestBody t: MutableList<Transaccion>): BlockChain {
        bc.agregarBloque(t)
        return get1()
    }

    @PostMapping("/validar")
    fun post2(@RequestBody i: MutableList<Bloque>): BlockChain {
        bc.reemplazarCadena(i)
        return get1()
    }


}