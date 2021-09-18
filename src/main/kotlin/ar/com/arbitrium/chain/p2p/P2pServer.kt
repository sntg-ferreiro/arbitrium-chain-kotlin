package ar.com.arbitrium.chain.p2p

import ar.com.arbitrium.chain.model.BlockChain
import ar.com.arbitrium.chain.model.Salida
import ar.com.arbitrium.chain.model.TipoMsjEnum
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import org.springframework.web.client.postForEntity
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*


@RestController
@RequestMapping("/p2p")
class P2pServer(@Autowired var restTemplate: RestTemplate, @Value("#{'\${p2p.pares}'.split(',')}") val pares: MutableList<String>) {
    //, @Autowired val props: ConfigProperties
    var bc: BlockChain = BlockChain()

    private val logger = LogFactory.getLog(javaClass)

    /**
     * esta deberia ir a buscar peers al discovery server, e intentar conectar.
     * deberia arrancar cuando el micro esta running.
     * lo primero que hay que hacer una vez estan conectados es intercambiar BC's
     * *
     */
    private fun searchPeersAndConnect(): Unit {
        logger.info("TODO! A COMPLETAR! - BUSCAR PARES Y CONECTAR")
        /**
         * cuando encuentro un par, le mando mi cadena?
         */
        logger.info("Buscando pares... ")
        this.pares.forEach { restTemplate.getForEntity<Unit>("$it/actuator/health") }

    }


    /**
     * Esto deberia suceder cada vez que un nuevo bloque es minado, o algun otro cambio surge en la cadena
     */
    fun syncCadenas(){
        logger.info("Sincronizando Cadenas")
        this.pares.forEach { enviarCadena(it) }
        logger.info("Cadenas sincronizadas con exito!")
    }

    private fun enviarCadena(p: String) {
        logger.info("Mandando cadena a: $p")
        try{
            val response: ResponseEntity<BlockChain> = restTemplate.postForEntity("$p/p2p/cadena", this.bc)
            response.body?.let { bc.reemplazarCadena(it.cadena) }
        }catch (e: Exception){
            logger.error("Error al enviar la cadena! Error: ${e.localizedMessage}")
        }
    }

    @GetMapping
    fun exe1(): MutableList<String> {
        return pares
    }

    /**
     * recibir el mensaje de un par. segun el tipo hacer la accion especificada
     * probablemente esto lo desglocemos (si es necesario) En varios Endpoints
     */
    @PostMapping
    fun recibirMensaje(@RequestParam tipo: String, @RequestBody cuerpo: LinkedHashMap<String, Any>){
        when(TipoMsjEnum.parse(tipo.uppercase(Locale.getDefault()))){
            TipoMsjEnum.CADENA  -> logger.info("TIPO: CADENA")
            //this.blockchain.replaceChain(chain);
            TipoMsjEnum.TRANSAC -> logger.info("TIPO: TRANSAC")
            //this.pool.updateOrAddTransaction(transaction);
            TipoMsjEnum.LIMPIAR -> logger.info("TIPO: LIMPIAR")
            //this.pool.clear();
        }
    }

    @PostMapping("/cadena")
    fun recibirCadena(@RequestBody cadena: BlockChain): BlockChain {
        logger.info("RECIBIENDO UNA CADENA -> ${cadena.cadena}")
        this.bc.reemplazarCadena(cadena.cadena)
        return this.bc
    }

    fun obtenerResultados(org: Long, dec: Long): MutableList<Salida> {
        return obtenerTodasLasSalidasDeOrg(org).filter { it.idDecision == dec } as MutableList<Salida>
    }

    fun obtenerTodasLasSalidasDeOrg(org: Long): MutableList<Salida>{
        val o: MutableList<Salida> = mutableListOf()
        for(b in this.bc.cadena){
            for (t in b.transacciones){
                if (t.entrada.idOrg == org) o.addAll(t.salidas)
            }
        }
        return o
    }

}