package frgp.utn.edu.app_padresprimerizos;

import java.time.LocalDate;

public class Guardia {
    private int idGuardia;
    private TipoGuardia tipo = TipoGuardia.MEDICA;
    private String nombre = "";
    private String ubicacion = "";
    private String telefono = "";
    private String horario = "";
    private LocalDate fechaGuardia;

    public int getIdGuardia() { return idGuardia; }
    public void setIdGuardia(int idGuardia) { this.idGuardia = idGuardia; }

    public TipoGuardia getTipo() { return tipo; }
    public void setTipo(TipoGuardia tipo) { this.tipo = tipo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public LocalDate getFechaGuardia() { return fechaGuardia; }
    public void setFechaGuardia(LocalDate fechaGuardia) { this.fechaGuardia = fechaGuardia; }
}
