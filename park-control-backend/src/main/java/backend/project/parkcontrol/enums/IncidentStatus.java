package backend.project.parkcontrol.enums;

public enum IncidentStatus {
    PENDIENTE(1),
    RESUELTO(2);

    private final int value;

    IncidentStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // Método para obtener el enum desde un entero
    public static IncidentStatus fromValue(int value) {
        for (IncidentStatus status : IncidentStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Valor de estado inválido: " + value);
    }
}
