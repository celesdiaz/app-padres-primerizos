package frgp.utn.edu.app_padresprimerizos;

public class Contacto {
    private int idContacto;
    private int idUsuario;
    private String nombre = "";
    private String telefono = "";
    private String tipoContacto = "";
    private boolean principal;

    public int getIdContacto() { return idContacto; }
    public void setIdContacto(int idContacto) { this.idContacto = idContacto; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getTipoContacto() { return tipoContacto; }
    public void setTipoContacto(String tipoContacto) { this.tipoContacto = tipoContacto; }

    public boolean isPrincipal() { return principal; }
    public void setPrincipal(boolean principal) { this.principal = principal; }
}