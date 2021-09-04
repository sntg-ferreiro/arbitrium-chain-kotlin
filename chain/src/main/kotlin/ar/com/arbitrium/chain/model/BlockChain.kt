package ar.com.arbitrium.chain.model

import java.util.logging.Logger
import kotlin.math.log

/**
 * Clase que contiene la cadena en si, con los metodos necesarios para ser utilizada.
 *
 * @author Z.F.
 */
data class BlockChain(var cadena: MutableList<Bloque> = mutableListOf(Bloque.genesis())) {
    companion object{
        val logger = Logger.getLogger(this::class.java.simpleName)
        /**
         *  validar cadena si:
         *    |-> Comienza con el mismo genesis
         *    |-> Los hashes de la cadena no fueron alteredos
         */
        fun validarCadena(cadena: MutableList<Bloque>) : Boolean {
            logger.info("Validando una cadena Nueva!")
            if (!cadena[0].equalsGenesis()) return false
            for(i in 1 until cadena.size){ //1 UNTIL CADENA.SIZE == 1 .. (CADENA.SIZE - 1)
                val anterior: Bloque = cadena[i-1]
                val actual: Bloque = cadena[i]
                if(anterior.hash != actual.hashAnterior || actual.hash != Bloque.hash(actual)) return false
            }
            logger.info("La cadena es valida!")
            return true
        }
    }

    fun agregarBloque(tx: MutableList<Transaccion>): Boolean {
        val bloque = Bloque.minar(cadena[cadena.size-1],tx)
        this.cadena.add(bloque)
        return true
    }

    /**
     * Reemplazar la cadena si:
     *   |-> la nueva cadena es mas larga && la nueva es valida
     */
    fun reemplazarCadena(cadenaNueva: MutableList<Bloque>): MutableList<Bloque> {
        logger.info("Estas a Punto de reemplazar la cadena existente!")
        if(cadenaNueva.size < this.cadena.size) return this.cadena
        else if(!validarCadena(cadenaNueva)) return this.cadena
        logger.info("La nueva cadena reemplazo la existente!")
        this.cadena = cadenaNueva
        return this.cadena
    }

}