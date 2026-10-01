package frgp.utn.edu.app_padresprimerizos

class Usuario {
    private var idUsuario: Int = 0
    private var nombre: String = ""
    private var apellido: String = ""
    private var email: String = ""
    private var contrasena: String = ""
    private var rol: Rol = Rol.USUARIO

    fun getIdUsuario(): Int = idUsuario
    fun setIdUsuario(idUsuario: Int) { this.idUsuario = idUsuario }

    fun getNombre(): String = nombre
    fun setNombre(nombre: String) { this.nombre = nombre }

    fun getApellido(): String = apellido
    fun setApellido(apellido: String) { this.apellido = apellido }

    fun getEmail(): String = email
    fun setEmail(email: String) { this.email = email }

    fun getContrasena(): String = contrasena
    fun setContrasena(contrasena: String) { this.contrasena = contrasena }

    fun getRol(): Rol = rol
    fun setRol(rol: Rol) { this.rol = rol }
}