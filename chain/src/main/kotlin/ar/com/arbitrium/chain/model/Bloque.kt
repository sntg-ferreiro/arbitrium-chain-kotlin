package ar.com.arbitrium.chain.model

import ar.com.arbitrium.chain.utils.*
import java.time.Instant
import kotlin.collections.ArrayList

/*
TODO:
static mine(oldBlock, newData){
static adjustDifficulty(oldBlock, currentTime){
 */
/**
 * representacion del bloque que formara la cadena.
 * nonce: campo que cambia cada vez que se intenta minar un bloque.
 *  es parte fundamental de como funciona HashChas
 * Dificultad: cantidad de 0's con los que tenia que empezar el hash cuando se mino el bloque.
 *  la idea de hacerla dinamica es que se pueda ajustar el ritmo al que se minan bloques
 *      -> se mantengan 'alrededor' de N milis
 * TODO: T -> Transacciones<Voto>
 * @author Z.F.
 */
data class Bloque(
    val instante: String,
    val hashAnterior: String,
    val hash: String,
    val nonce: Int,
    val dificultad: Int,
    val transacciones: MutableList<Transaccion>
) {
    companion object{
        fun genesis(): Bloque{
            return Bloque(Instant.now().toString(),
                "----------------",
                "4rb1tr1vm-ch41nz",
                0, 0, ArrayList())
        }

        /**
         * Que fantastico es KOTLIN VIEJA x2
         */
        fun hash(b:Bloque):String {
            val (anterior,instanteActual,hashActual,transaccionesActuales, nonce2) = b
            return "$anterior$instanteActual$hashActual$transaccionesActuales$nonce2".hash(Algorithm.SHA256)
            }

        fun minar(tx: Transaccion): Bloque {
            //TODO("Not yet implemented")
            var b = genesis()
            b.transacciones.add(tx)
            return b
        }

    }

    /**
     * Que fantastico es KOTLIN VIEJA
     */
    fun hash(): String {
        return "$hashAnterior$instante$hash$transacciones$nonce".hash(Algorithm.SHA256);
    }

    fun equalsGenesis(): Boolean = this == genesis()

}