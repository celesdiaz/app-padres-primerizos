package frgp.utn.edu.app_padresprimerizos;

import java.time.LocalDate;

public class Bebe {
    private int idBebe;
    private int idUsuario;
    private String nombre = "";
    private LocalDate fechaNacimiento;

    public int getIdBebe() { return idBebe; }
    public void setIdBebe(int idBebe) { this.idBebe = idBebe; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
}
