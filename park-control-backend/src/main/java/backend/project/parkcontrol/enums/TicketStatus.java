package backend.project.parkcontrol.enums;

public enum TicketStatus {
    ENTRADA_REGISTRADA(1),
    SALIDA_REGISTRADA(2),
    TICKET_PAGADO(3),
    INCIDENTE(4);

    private final int value;

    TicketStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // Método para obtener el enum desde un entero
    public static TicketStatus fromValue(int value) {
        for (TicketStatus status : TicketStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Valor de estado inválido: " + value);
    }
}
