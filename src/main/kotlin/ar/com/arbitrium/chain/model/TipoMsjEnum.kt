package ar.com.arbitrium.chain.model

enum class TipoMsjEnum {
    CADENA, TRANSAC, LIMPIAR;
companion object{
    fun parse(i: String): TipoMsjEnum{
        return valueOf(i)
    }
}

}
