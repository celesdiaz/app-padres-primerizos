package frgp.utn.edu.app_padresprimerizos;

public enum TipoServicio {
    FARMACIA("Farmacia"),
    PEDIATRIA("Pediatría"),
    GUARDERIA("Guardería"),
    PANALERA("Pañalera");

    // texto que se muestra al usuario (con tildes); en la base se guarda name()
    private final String etiqueta;

    TipoServicio(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
