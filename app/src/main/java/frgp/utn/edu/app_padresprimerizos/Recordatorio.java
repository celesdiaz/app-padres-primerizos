package frgp.utn.edu.app_padresprimerizos;

import java.time.LocalDate;
public class Recordatorio {

    private int idRecordatorio = 0;
    private int idBebe = 0;
    private Integer idVacuna = null;
    private TipoRecordatorio tipo = TipoRecordatorio.VACUNA;
    private LocalDate fecha = null;
    private String descripcion = "";
    private EstadoRecordatorio estado = EstadoRecordatorio.PENDIENTE;

    public int getIdRecordatorio() { return idRecordatorio; }
    public void setIdRecordatorio(int idRecordatorio) { this.idRecordatorio = idRecordatorio; }

    public int getIdBebe() { return idBebe; }
    public void setIdBebe(int idBebe) { this.idBebe = idBebe; }

    public Integer getIdVacuna() { return idVacuna; }
    public void setIdVacuna(Integer idVacuna) { this.idVacuna = idVacuna; }

    public TipoRecordatorio getTipo() { return tipo; }
    public void setTipo(TipoRecordatorio tipo) { this.tipo = tipo; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public EstadoRecordatorio getEstado() { return estado; }
    public void setEstado(EstadoRecordatorio estado) { this.estado = estado; }
}
