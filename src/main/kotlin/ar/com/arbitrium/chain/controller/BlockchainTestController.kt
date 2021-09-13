package ar.com.arbitrium.chain.controller

import ar.com.arbitrium.chain.model.BlockChain
import ar.com.arbitrium.chain.model.Bloque
import ar.com.arbitrium.chain.model.Transaccion
import ar.com.arbitrium.chain.p2p.P2pServer
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bc")
class BlockchainTestController(val p2pServer: P2pServer) {

    @GetMapping
    fun get1(): BlockChain = p2pServer.bc

    @PostMapping
    fun post1(@RequestBody t: MutableList<Transaccion>): BlockChain {
        p2pServer.bc.agregarBloque(t)
        p2pServer.syncCadenas()
        return get1()
    }

    @PostMapping("/reemplazar")
    fun post2(@RequestBody i: MutableList<Bloque>): BlockChain {
        p2pServer.bc.reemplazarCadena(i)
        return get1()
    }


    @GetMapping("/resultados/{org}/{dec}")
    fun obtenerResultados(@PathVariable("org") org: Long, @PathVariable("dec") dec: Long) =
        p2pServer.obtenerResultados(org, dec)

}