package backend.project.parkcontrol.enums;

public enum IncidentTypes {
    COMPROBANTE_EXTRAVIADO(1),
    ACREDITACION_PROPIEDAD(2);

    private final int value;

    IncidentTypes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // Método para obtener el enum desde un entero
    public static IncidentTypes fromValue(int value) {
        for (IncidentTypes status : IncidentTypes.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Valor de estado inválido: " + value);
    }
}
