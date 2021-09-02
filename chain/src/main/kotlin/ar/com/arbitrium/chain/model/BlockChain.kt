package ar.com.arbitrium.chain.model

/**
 * Clase que contiene la cadena en si, con los metodos necesarios para ser utilizada.
 *
 * @author Z.F.
 */
data class BlockChain(var cadena: MutableList<Bloque> = mutableListOf(Bloque.genesis())) {
    companion object{
        /**
         *  validar cadena si:
         *    |-> Comienza con el mismo genesis
         *    |-> Los hashes de la cadena no fueron alteredos
         */
        fun validarCadena(cadena: MutableList<Bloque>) : Boolean {
            if (!cadena[0].equalsGenesis()) return false
            for(i in 1..cadena.size){
                val anterior: Bloque = cadena[i-1]
                val actual: Bloque = cadena[i]
                if(anterior.hash != actual.hashAnterior || actual.hash != Bloque.hash(actual)) return false
            }
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
        if(cadenaNueva.size < cadena.size) return cadena
        else if(!validarCadena(cadenaNueva)) return cadena
        cadena = cadenaNueva
        return cadena
    }

}