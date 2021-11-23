package ar.com.arbitrium.chain.configuration

import ar.com.arbitrium.chain.p2p.P2pServer
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class ApplicationReadyListener @Autowired constructor(val p2pServer: P2pServer): ApplicationListener<ApplicationReadyEvent> {
    private val logger: Log = LogFactory.getLog(javaClass)
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        try{
            p2pServer.syncCadenas()
        }catch (e: Exception){
            logger.error("ERROR EN READY-EVENT: ${e.localizedMessage}")
        }
    }

}