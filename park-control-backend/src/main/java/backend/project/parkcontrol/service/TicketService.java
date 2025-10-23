package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewTicketDto;
import backend.project.parkcontrol.dto.response.TicketDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.TicketCrud;
import backend.project.parkcontrol.repository.entities.Branch;
import backend.project.parkcontrol.repository.entities.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketService {
    private final TicketCrud ticketCrud;
    private final BranchService branchService;

    // ==============================
    // GETTERS
    // ==============================
    public List<Ticket> getById_branch(Integer id){
        List<Ticket> list = ticketCrud.findById_branch(id);
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontraron registros para la sucursal");
        return list;
    }

    public ResponseSuccessfullyDto getById_branchResponse(Integer id){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con éxito")
                .body(getById_branch(id))
                .build();
    }

    public List<Ticket> getAllTicketList(){
        List<Ticket> list = ticketCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No hay registros");
        return list;
    }

    public ResponseSuccessfullyDto getAllTicketListResponse(){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con éxito")
                .body(getAllTicketList())
                .build();
    }

    public Ticket getTicketById(Integer id){
        return ticketCrud.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Registro no encontrado"));
    }

    public ResponseSuccessfullyDto getTicketByIdResponse(Integer id){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registro encontrado con éxito")
                .body(getTicketById(id))
                .build();
    }

    // ==============================
    // CRUD
    // ==============================
    public ResponseSuccessfullyDto createTicket(NewTicketDto dto){
        checkAvailability(dto);
        Ticket e = new Ticket();
        e.setBranch(branchService.getBranchById(dto.getId_branch()));
        e.setPlate(dto.getPlate());
        //e.setCard(dto.getCard());
        //e.setQr(dto.getQr());
        e.setEntry_date(LocalDateTime.now());
        //e.setExit_date(dto.getExit_date());
        e.setIs_4r(dto.getIs_4r());
        //e.setStatus(dto.getStatus());
        Ticket savedTicket = ticketCrud.save(e);
        //
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Registro creado con éxito")
                .body(Map.of("id", savedTicket.getId()))
                .build();
    }

    private void checkAvailability(NewTicketDto dto) {
        Branch branch = branchService.getBranchById(dto.getId_branch());
        if (dto.getIs_4r()){
            Integer ocupation_4r = branch.getOcupation_4r();
            if (ocupation_4r+1>branch.getCapacity_4r()){
                throw new BusinessException(HttpStatus.BAD_REQUEST,
                        "No hay espacio disponible para ingresar al parqueo con un vehículo de 4 ruedas.");
            }
        }else{
            Integer ocupation_2r = branch.getOcupation_2r();
            if (ocupation_2r+1>branch.getCapacity_2r()){
                throw new BusinessException(HttpStatus.BAD_REQUEST,
                        "No hay espacio disponible para ingresar al parqueo con un vehículo de 2 ruedas.");
            }
        }
    }

    public ResponseSuccessfullyDto updateTicket(TicketDto dto){
        Ticket existing = getTicketById(dto.getId());
        existing.setBranch(branchService.getBranchById(dto.getId_branch()));
        existing.setPlate(dto.getPlate());
        existing.setCard(dto.getCard());
        existing.setQr(dto.getQr());
        existing.setEntry_date(dto.getEntry_date());
        existing.setExit_date(dto.getExit_date());
        existing.setIs_4r(dto.getIs_4r());
        existing.setStatus(dto.getStatus());

        ticketCrud.save(existing);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Registro actualizado con éxito")
                .build();
    }

    public ResponseSuccessfullyDto deleteTicket(Integer id){
        Ticket entity = getTicketById(id);
        ticketCrud.delete(entity);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Registro eliminado con éxito")
                .build();
    }
}
