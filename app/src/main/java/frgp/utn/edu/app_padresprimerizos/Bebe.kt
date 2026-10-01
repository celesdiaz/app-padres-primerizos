package frgp.utn.edu.app_padresprimerizos

import java.util.Date

class Bebe {
    private var idBebe: Int = 0
    private var idUsuario: Int = 0
    private var nombre: String = ""
    private var fechaNacimiento: Date? = null

    fun getIdBebe(): Int = idBebe
    fun setIdBebe(idBebe: Int) { this.idBebe = idBebe }

    fun getIdUsuario(): Int = idUsuario
    fun setIdUsuario(idUsuario: Int) { this.idUsuario = idUsuario }

    fun getNombre(): String = nombre
    fun setNombre(nombre: String) { this.nombre = nombre }

    fun getFechaNacimiento(): Date? = fechaNacimiento
    fun setFechaNacimiento(fechaNacimiento: Date?) { this.fechaNacimiento = fechaNacimiento }
}