package frgp.utn.edu.app_padresprimerizos

class Vacuna {
    private var idVacuna: Int = 0
    private var nombre: String = ""
    private var edadAplicacion: Int = 0

    fun getIdVacuna(): Int = idVacuna
    fun setIdVacuna(idVacuna: Int) { this.idVacuna = idVacuna }

    fun getNombre(): String = nombre
    fun setNombre(nombre: String) { this.nombre = nombre }

    fun getEdadAplicacion(): Int = edadAplicacion
    fun setEdadAplicacion(edadAplicacion: Int) { this.edadAplicacion = edadAplicacion }
}