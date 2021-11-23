package ar.com.arbitrium.chain.model

import ar.com.arbitrium.chain.utils.Algorithm
import ar.com.arbitrium.chain.utils.hash
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

/**
 * representacion del bloque que formara la cadena.
 * nonce: campo que cambia cada vez que se intenta minar un bloque.
 *  es parte fundamental de como funciona HashCash
 * Dificultad: cantidad de 0's con los que tenia que empezar el hash cuando se mino el bloque.
 *  la idea de hacerla dinamica es que se pueda ajustar el ritmo al que se minan bloques
 *      -> se mantengan 'alrededor' de N milis
 * @author Z.F.
 */
data class Bloque(
    val instante: Long,
    val hashAnterior: String,
    val hash: String,
    val nonce: Int,
    val dificultad: Int,
    val transacciones: MutableList<Transaccion>
) {
    private val logger = LogFactory.getLog(javaClass)
    companion object{

        private val logger: Log = LogFactory.getLog(this::class.java)
        private const val INTERVALO_MINERO: Long = 0
        fun genesis(): Bloque{
            return Bloque(0,//System.currentTimeMillis(),
                "----------------",
                "4rb1tr1vm-ch41nz",
                0, 4, ArrayList())
        }

        /**
         * Que fantastico es KOTLIN VIEJA
         */
        fun hash(hashAnterior:String,instante: Long,transacciones: MutableList<Transaccion>,nonce: Int): String {
            return "$hashAnterior$instante$transacciones$nonce".hash(Algorithm.SHA256);
        }

        fun hash(b:Bloque):String {
            val anterior = b.hashAnterior
            val instanteActual = b.instante
            val transaccionesActuales = b.transacciones
            val nonce2 = b.nonce
            return hash(anterior, instanteActual, transaccionesActuales, nonce2)
        }

        fun minar(bloqueViejo: Bloque, txs: MutableList<Transaccion>, diff: Int?=null): Bloque {
            val hashAnterior: String = bloqueViejo.hash
            val instanteAnterior: Long = bloqueViejo.instante
            var dificultad: Int = diff ?: bloqueViejo.dificultad
            var hash: String = ""
            var instante: Long = 0
            var nonce = 0
            do{
                nonce++
                instante = System.currentTimeMillis()
                dificultad = ajustarDif(instanteAnterior, instante, dificultad)
                hash = hash(hashAnterior, instante, txs, nonce)
            }while (hash.subSequence(0,dificultad) != "0".repeat(dificultad))
            logger.info("[NUEVO BLOQUE MINADO] -> Nonce: $nonce - Diff: $dificultad - Hash: $hash")
            return Bloque(instante, hashAnterior, hash, nonce, dificultad, txs)
        }


        /**
         * /*(instanteAnterior + INTERVALO_MINERO < instante) difAnterior //+ 1
        else difAnterior //-1*/
         */
        fun ajustarDif(instanteAnterior: Long,instante: Long, difAnterior: Int): Int{
            return difAnterior

        }

    }

    fun equalsGenesis(): Boolean = this == genesis()
}