package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewTicketDto;
import backend.project.parkcontrol.dto.response.TicketDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.enums.TicketStatus;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.BranchCrud;
import backend.project.parkcontrol.repository.crud.TicketCrud;
import backend.project.parkcontrol.repository.entities.Branch;
import backend.project.parkcontrol.repository.entities.Ticket;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketService {
    private final TicketCrud ticketCrud;
    private final BranchService branchService;
    private final BranchCrud branchCrud;
    private final TicketUsageService ticketUsageService;
    // ==============================
    // GETTERS
    // ==============================
    public List<Ticket> getById_branch(Integer id){
           return ticketCrud.findById_branch(id);
    }

    public ResponseSuccessfullyDto getById_branchResponse(Integer id){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con éxito")
                .body(getById_branch(id))
                .build();
    }

    public List<Ticket> getByPlate(String plate){
        return ticketCrud.findByPlate(plate);
    }

    public ResponseSuccessfullyDto getByPlateResponse(String plate){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con éxito")
                .body(getByPlate(plate))
                .build();
    }

    public List<Ticket> getAllTicketList(){
        return ticketCrud.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public ResponseSuccessfullyDto getAllTicketListResponse(){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con éxito")
                .body(getAllTicketList())
                .build();
    }

    public Ticket getTicketById(Integer id){
        return ticketCrud.findById(id).get();
    }

    public ResponseSuccessfullyDto getTicketByIdResponse(Integer id){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registro encontrado con éxito")
                .body(getTicketById(id))
                .build();
    }

    public List<Ticket> getByIdbranchStatus(Integer idBranch, Integer status){
        return ticketCrud.findByIdBranchStatus(idBranch,status);
    }

    public ResponseSuccessfullyDto getByIdbranchStatusResponse(Integer idBranch, Integer status){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con éxito")
                .body(getByIdbranchStatus(idBranch, status))
                .build();
    }

    // ==============================
    // CRUD
    // ==============================
    public ResponseSuccessfullyDto createTicket(NewTicketDto dto){
        checkAvailability(dto);
        checkPlate(dto);
        Ticket e = new Ticket();
        e.setBranch(branchService.getBranchById(dto.getId_branch()));
        e.setPlate(dto.getPlate());
        e.setEntry_date(LocalDateTime.now(ZoneId.of("America/Guatemala")));
        e.setIs_4r(dto.getIs_4r());
        e.setStatus(TicketStatus.ENTRADA_REGISTRADA.getValue());
        Ticket savedTicket = ticketCrud.save(e);
        //AGREGAMOS LOS CAMPOS FALTANTES
        savedTicket.setCard(generateCardValue(savedTicket.getId()+savedTicket.getPlate()));
        savedTicket.setQr(generateQrBase64(savedTicket.getCard()));
        ticketCrud.save(savedTicket);
        //EDITAMOS EL TICKET_USAGE
        ticketUsageService.updateRateAssignament(savedTicket);
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Registro creado con éxito")
                .body(Map.of("id", savedTicket.getId()))
                .build();
    }

    private void checkPlate(NewTicketDto dto) {
        if (!ticketCrud.findByPlateStatus(dto.getPlate(), 1).isEmpty()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "El vehículo ya se encuentra registrado dentro de un Parqueo.");
        }
    }

    private void checkAvailability(NewTicketDto dto) {
        Branch branch = branchService.getBranchById(dto.getId_branch());
        if (dto.getIs_4r()){
            Integer ocupation_4r = branch.getOcupation_4r();
            if (ocupation_4r+1>branch.getCapacity_4r()){
                throw new BusinessException(HttpStatus.BAD_REQUEST,
                        "No hay espacio disponible para ingresar al parqueo con un vehículo de 4 ruedas.");
            }else{
                branch.setOcupation_4r(ocupation_4r+1);
                branchCrud.save(branch);
                log.info("Ocupacion de "+branch.getName()+" fue actualizada");
            }
        }else{
            Integer ocupation_2r = branch.getOcupation_2r();
            if (ocupation_2r+1>branch.getCapacity_2r()){
                throw new BusinessException(HttpStatus.BAD_REQUEST,
                        "No hay espacio disponible para ingresar al parqueo con un vehículo de 2 ruedas.");
            }else{
                branch.setOcupation_2r(ocupation_2r+1);
                branchCrud.save(branch);
                log.info("Ocupacion de "+branch.getName()+" fue actualizada");
            }
        }
    }

    public ResponseSuccessfullyDto closeTicket(String data, Integer typeData){
        Ticket ticket;
        if (typeData.equals(1)){//PLACA
            ticket = ticketCrud.findByPlateStatus(data,TicketStatus.ENTRADA_REGISTRADA.getValue()).getFirst();
        } else if (typeData.equals(2)) {//TARJETA
            ticket = ticketCrud.findByCard(data).getFirst();
        } else {
            ticket = ticketCrud.findById(Integer.valueOf(data)).get();
        }

        if (ticket.getStatus()!=TicketStatus.ENTRADA_REGISTRADA.getValue()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "El vehículo ya no se encuentra dentro del parqueo.");
        }
        ticket.setStatus(TicketStatus.SALIDA_REGISTRADA.getValue());
        ticket.setExit_date(LocalDateTime.now(ZoneId.of("America/Guatemala")));
        //UPDATE TICKET CONSUMO
        ticketUsageService.calculatePayment(ticket);
        ticketCrud.save(ticket);
        updateAvailability(ticket);
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Ticket cerrado")
                .body(ticket)
                .build();
    }

    private void updateAvailability(Ticket ticket) {
        Branch branch = ticket.getBranch();
        if (ticket.getIs_4r()){
            branch.setOcupation_4r(branch.getOcupation_4r()-1);
        }else{
            branch.setOcupation_2r(branch.getOcupation_2r()-1);
        }
        log.info("Se actualizo la ocupacion de la sucursal");
        branchCrud.save(branch);
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
                .code(HttpStatus.OK)
                .message("Registro actualizado con éxito")
                .build();
    }

    public ResponseSuccessfullyDto deleteTicket(Integer id){
        Ticket entity = getTicketById(id);
        ticketCrud.delete(entity);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro eliminado con éxito")
                .build();
    }

    public String generateQrBase64(String content) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            var bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 200, 200);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();

            // Convertimos a Base64
            return Base64.getEncoder().encodeToString(pngData);

        } catch (WriterException | IOException e) {
            throw new RuntimeException("Error generando código QR", e);
        }
    }

    public String generateCardValue(String value) {
        // Convierte a bytes usando UTF-8
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        // Codifica en Base64 para obtener un string seguro
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
