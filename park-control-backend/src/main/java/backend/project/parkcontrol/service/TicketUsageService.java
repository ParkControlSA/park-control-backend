package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewTicketUsageDto;
import backend.project.parkcontrol.dto.response.TicketUsageDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.enums.TicketStatus;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.TicketCrud;
import backend.project.parkcontrol.repository.crud.TicketPaymentCrud;
import backend.project.parkcontrol.repository.crud.TicketUsageCrud;
import backend.project.parkcontrol.repository.entities.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketUsageService {
    private final TicketCrud ticketCrud;
    private final TicketUsageCrud ticketusageCrud;
    private final RateAssignmentService rateAssignmentService;
    private final ContractService contractService;
    private final ContractHistoryService contractHistoryService;
    private final TicketPaymentCrud ticketpaymentCrud;
    private static final Integer ID_MAIN_BRANCH = 1;
    private static final Integer PAID_BY_PLAN = 3;
    private static final Integer PAID_BY_BUSSINES = 4;

    // ==============================
    // GETTERS
    // ==============================
    public List<TicketUsage> getById_ticket(Integer id) {
        List<TicketUsage> list = ticketusageCrud.findById_ticket(id);
        return list;
    }

    public ResponseSuccessfullyDto getById_ticketResponse(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con éxito")
                .body(getById_ticket(id))
                .build();
    }

    public List<TicketUsage> getAllTicketUsageList() {
        List<TicketUsage> list = ticketusageCrud.findAll();
        return list;
    }

    public ResponseSuccessfullyDto getAllTicketUsageListResponse() {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con éxito")
                .body(getAllTicketUsageList())
                .build();
    }

    public TicketUsage getTicketUsageById(Integer id) {
        return ticketusageCrud.findById(id).orElse(new TicketUsage());
    }

    public ResponseSuccessfullyDto getTicketUsageByIdResponse(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registro encontrado con éxito")
                .body(getTicketUsageById(id))
                .build();
    }

    // ==============================
    // CRUD
    // ==============================
    public ResponseSuccessfullyDto createTicketUsage(NewTicketUsageDto dto) {
        TicketUsage e = new TicketUsage();
        //e.setTicket(ticketService.getTicketById(dto.getId_ticket()));
        e.setGranted_hours(dto.getGranted_hours());
        e.setConsumed_plan_hours(dto.getConsumed_plan_hours());
        e.setExceeded_hours(dto.getExceeded_hours());
        e.setTotal_hours(dto.getTotal_hours());
        e.setHourly_rate(dto.getHourly_rate());
        e.setCustomer_amount(dto.getCustomer_amount());

        ticketusageCrud.save(e);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Registro creado con éxito")
                .build();
    }

    public ResponseSuccessfullyDto updateTicketUsage(TicketUsageDto dto) {
        TicketUsage existing = getTicketUsageById(dto.getId());
        //existing.setTicket(ticketService.getTicketById(dto.getId_ticket()));
        existing.setGranted_hours(dto.getGranted_hours());
        existing.setConsumed_plan_hours(dto.getConsumed_plan_hours());
        existing.setExceeded_hours(dto.getExceeded_hours());
        existing.setTotal_hours(dto.getTotal_hours());
        existing.setHourly_rate(dto.getHourly_rate());
        existing.setCustomer_amount(dto.getCustomer_amount());

        ticketusageCrud.save(existing);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro actualizado con éxito")
                .build();
    }

    public ResponseSuccessfullyDto deleteTicketUsage(Integer id) {
        TicketUsage entity = getTicketUsageById(id);
        ticketusageCrud.delete(entity);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro eliminado con éxito")
                .build();
    }

    public void updateRateAssignament(Ticket ticket) {
        TicketUsage existing = getById_ticket(ticket.getId()).getFirst();
        List<RateAssignment> rateAssignment = rateAssignmentService.getRateAssignamentById_branchIsActive(ticket.getBranch().getId());
        Double rate;
        if (!rateAssignment.isEmpty()) {
            rate = rateAssignment.getFirst().getHourly_rate();
        } else {
            rate = rateAssignmentService.getRateAssignamentById_branchIsActive(ID_MAIN_BRANCH).getFirst().getHourly_rate();
        }
        existing.setHourly_rate(rate);
        ticketusageCrud.save(existing);

        log.info("Tarifa Hora Aplicada.");
    }

    public void calculatePayment(Ticket ticket) {
        TicketUsage existing = getById_ticket(ticket.getId()).getFirst();
        //VERIFICAMOS SI TIENE CONTRATO ACTIVO
        List<Contract> contract = contractService.getByLicense_plateIsActive(ticket.getPlate());
        boolean isContract = !contract.isEmpty();
        //DEFINIMOS EL PLAN HOURS Y EL CONTRATO HISTORY
        int planHours = 0;
        ContractHistory contractHistory1 = null;
        if (isContract) {//CLIENTE CON SUB
            Contract contract1 = contract.getFirst();
            java.sql.Date entryDateSql = java.sql.Date.valueOf(ticket.getEntry_date().toLocalDate());
            List<ContractHistory> contractHistory = contractHistoryService.findByContractAndDate(contract1.getId(), entryDateSql);
            if (!contractHistory.isEmpty()) {
                contractHistory1 = contractHistory.getFirst();
                planHours = contractHistory1.getIncluded_hours() - contractHistory1.getConsumed_hours();
            } else {
                log.info("Su plan no cubre el día " + ticket.getEntry_date().getDayOfWeek().toString());
            }
        }//CLIENTE GENERAL
        Integer parkHours = calculateHours(ticket.getEntry_date(), ticket.getExit_date());
        existing.setConsumed_hours(parkHours);
        existing.setTotal_hours(parkHours);
        if (existing.getGranted_hours() > 0) {
            createTicketPayment(ticket, 0.0, PAID_BY_BUSSINES);
        }
        int hoursExceed = parkHours - existing.getGranted_hours();//HORAS RESTANTES = HORAS PARQUEADO - HORAS OTORGADAS POR COMERCIOS
        if (hoursExceed > 0) {//SI EXISTEN HORAS POR PAGAR
            if (planHours != 0) {//SI HAY PLAN Y TIENE HORAS DISPONIBLES
                hoursExceed = hoursExceed - planHours;//HORAS RESTANTES = HORAS RESTANTES - HORAS DISPONIBLES DEL PLAN
                if (hoursExceed <= 0) {//SI NO EXISTEN HORAS POR PAGAR CON PLAN
                    existing.setExceeded_hours(0);
                    contractHistory1.setConsumed_hours(contractHistory1.getIncluded_hours() - (hoursExceed * (-1)));
                    ticket.setStatus(TicketStatus.TICKET_PAGADO.getValue());
                } else {//SI EXISTEN HORAS POR PAGAR CON PLAN
                    existing.setExceeded_hours(hoursExceed);
                    contractHistory1.setConsumed_hours(contractHistory1.getIncluded_hours());

                }
                createTicketPayment(ticket, 0.0, PAID_BY_PLAN);
                existing.setConsumed_plan_hours(contractHistory1.getIncluded_hours() - contractHistory1.getConsumed_hours());
            } else {
                existing.setExceeded_hours(hoursExceed);
            }
        } else {//SI NO EXISTEN HORAS POR PAGAR
            existing.setExceeded_hours(0);
            ticket.setStatus(TicketStatus.TICKET_PAGADO.getValue());
        }
        existing.setCustomer_amount(existing.getExceeded_hours() * existing.getHourly_rate());
        ticketusageCrud.save(existing);
        ticketCrud.save(ticket);
    }

    public int calculateHours(LocalDateTime inicio, LocalDateTime fin) {
        // Si la fecha final es antes que la inicial, lanza excepción o devuelve 0
        if (fin.isBefore(inicio)) {
            throw new IllegalArgumentException("La fecha final no puede ser anterior a la inicial");
        }

        // Calculamos la diferencia total en minutos
        long minutos = Duration.between(inicio, fin).toMinutes();

        // Convertimos a horas con redondeo hacia arriba
        double horas = Math.ceil(minutos / 60.0);

        // Mínimo 1 hora
        return (int) Math.max(horas, 1);
    }

    public void createTicketPayment(Ticket ticket, Double monto, int paymentMethod) {
        TicketPayment e = new TicketPayment();
        e.setTicket(ticket);
        e.setTotal_amount(monto);
        e.setDate(LocalDateTime.now(ZoneId.of("America/Guatemala")));
        e.setPayment_method(paymentMethod);
        ticketpaymentCrud.save(e);
    }
}
