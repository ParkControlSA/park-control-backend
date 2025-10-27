package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewTicketBusinessUsageDto;
import backend.project.parkcontrol.dto.response.TicketBusinessUsageDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.TicketBusinessUsageCrud;
import backend.project.parkcontrol.repository.entities.TicketBusinessUsage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketBusinessUsageService {
    private final TicketBusinessUsageCrud ticketbusinessusageCrud;
    private final AffiliatedBusinessService affiliatedBusinessService;
    private final TicketUsageService ticketUsageService;
    private final ValidationService validationService;
    // ==============================
    // GETTERS
    // ==============================
    public List<TicketBusinessUsage> getById_ticket_usage(Integer id){
        List<TicketBusinessUsage> list = ticketbusinessusageCrud.findById_ticket_usage(id);
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontraron registros para el ticket usage");
        return list;
    }

    public ResponseSuccessfullyDto getById_ticket_usageResponse(Integer id){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getById_ticket_usage(id))
                .build();
    }

    public List<TicketBusinessUsage> getById_affiliated_business(Integer id){
        List<TicketBusinessUsage> list = ticketbusinessusageCrud.findById_affiliated_business(id);
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontraron registros para el negocio afiliado");
        return list;
    }

    public ResponseSuccessfullyDto getById_affiliated_businessResponse(Integer id){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getById_affiliated_business(id))
                .build();
    }

    public List<TicketBusinessUsage> getAllTicketBusinessUsageList(){
        List<TicketBusinessUsage> list = ticketbusinessusageCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No hay registros");
        return list;
    }

    public ResponseSuccessfullyDto getAllTicketBusinessUsageListResponse(){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getAllTicketBusinessUsageList())
                .build();
    }

    public TicketBusinessUsage getTicketBusinessUsageById(Integer id){
        return ticketbusinessusageCrud.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Registro no encontrado"));
    }

    public ResponseSuccessfullyDto getTicketBusinessUsageByIdResponse(Integer id){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registro encontrado con Éxito")
                .body(getTicketBusinessUsageById(id))
                .build();
    }

    // ==============================
    // CRUD
    // ==============================
    public ResponseSuccessfullyDto createTicketBusinessUsage(NewTicketBusinessUsageDto dto){
        TicketBusinessUsage e = new TicketBusinessUsage();
        verifyData(dto);
        e.setTicketUsage(ticketUsageService.getTicketUsageById(dto.getId_ticket_usage()));
        e.setAffiliatedBusiness(affiliatedBusinessService.getAffiliatedBusinessById(dto.getId_affiliated_business()));
        e.setGranted_hours(dto.getGranted_hours());

        ticketbusinessusageCrud.save(e);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Registro creado con Éxito")
                .build();
    }

    private void verifyData(NewTicketBusinessUsageDto dto) {
        validationService.validatePositiveNumber(dto.getGranted_hours(), "Horas Otorgadas del Comercio");
        if (!ticketbusinessusageCrud.findById_affiliated_businessId_ticket_usage(dto.getId_affiliated_business(), dto.getId_ticket_usage()).isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "Ya se han asignado horas gratuitas a este ticket.");
        }

    }

    public ResponseSuccessfullyDto updateTicketBusinessUsage(TicketBusinessUsageDto dto){
        TicketBusinessUsage existing = getTicketBusinessUsageById(dto.getId());
        existing.setTicketUsage(ticketUsageService.getTicketUsageById(dto.getId_ticket_usage()));
        existing.setAffiliatedBusiness(affiliatedBusinessService.getAffiliatedBusinessById(dto.getId_affiliated_business()));
        existing.setGranted_hours(dto.getGranted_hours());

        ticketbusinessusageCrud.save(existing);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro actualizado con Éxito")
                .build();
    }

    public ResponseSuccessfullyDto deleteTicketBusinessUsage(Integer id){
        TicketBusinessUsage entity = getTicketBusinessUsageById(id);
        ticketbusinessusageCrud.delete(entity);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro eliminado con Éxito")
                .build();
    }
}
