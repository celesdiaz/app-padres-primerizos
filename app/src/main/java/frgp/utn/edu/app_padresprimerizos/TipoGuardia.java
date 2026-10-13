package frgp.utn.edu.app_padresprimerizos;

public enum TipoGuardia {
    MEDICA("Médica"),
    FARMACEUTICA("Farmacéutica");

    // texto que se muestra al usuario, en la base se guarda name()
    private final String etiqueta;

    TipoGuardia(String etiqueta)
    {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta()
    {
        return etiqueta;
    }
}
