package ar.com.arbitrium.chain.model

data class Transaccion(
    var id: Long,
    var idOrg: Long,
    var idMiembro: Long,
    var idVotacion: Long,
    var idOpcion: Long
    ) {

}
