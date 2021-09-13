package ar.com.arbitrium.chain.model

import java.util.*

/**
 * Representa todas las decisiones tomadas por 1 miembro de una org
 * 1 'decididor' <-> N opciones tomadas
 * @author Z.F.
 */
data class Transaccion(
    var id: String = UUID.randomUUID().toString(),
    var entrada: Entrada,
    var salidas: MutableList<Salida>
    ) {

}

/**
 * identifica a 1 miembro de 1 org
 */
data class Entrada(var idOrg: Long,
                   var idMiembro: Long,){
    fun compare(input: Entrada): Boolean{
        return input.idMiembro == this.idMiembro && input.idOrg == this.idOrg
    }

}

/**
 * identifica 1 opcion elegida de cada decision abierta.
 */
data class Salida(var idDecision: Long,
                  var idOpcion: Long){

}
