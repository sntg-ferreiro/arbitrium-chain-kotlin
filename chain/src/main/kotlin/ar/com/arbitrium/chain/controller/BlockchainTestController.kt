package ar.com.arbitrium.chain.controller

import ar.com.arbitrium.chain.model.BlockChain
import ar.com.arbitrium.chain.model.Bloque
import ar.com.arbitrium.chain.model.Transaccion
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bc")
class BlockchainTestController(val bc: BlockChain = BlockChain()) {

    @GetMapping
    fun get1(): MutableList<Bloque> = bc.cadena

    @PostMapping
    fun post1(@RequestBody t: MutableList<Transaccion>): MutableList<Bloque> {
        bc.agregarBloque(t)
        return get1()
    }

    @PostMapping("/reemplazar")
    fun post2(@RequestBody i: MutableList<Bloque>): MutableList<Bloque> {
        bc.reemplazarCadena(i)
        return get1()
    }


}