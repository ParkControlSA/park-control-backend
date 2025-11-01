package backend.project.parkcontrol.enums;

public enum LicensePlateBlockRequestStatus {
    PENDIENTE(1),
    APROBADA(2),
    RECHAZADA(3),
    REVOCADA(4);

    private final int value;

    LicensePlateBlockRequestStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // Método para obtener el enum desde un entero
    public static LicensePlateBlockRequestStatus fromValue(int value) {
        for (LicensePlateBlockRequestStatus status : LicensePlateBlockRequestStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Valor de estado inválido: " + value);
    }
}
