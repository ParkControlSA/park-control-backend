package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewTemporaryContractPermitDto;
import backend.project.parkcontrol.dto.response.TemporaryContractPermitDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.TemporaryContractPermitCrud;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.TemporaryContractPermit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TemporaryContractPermitService {
    private final TemporaryContractPermitCrud temporarycontractpermitCrud;
    private final ContractService contractService;
    private final UserCrud userCrud;

    // ==============================
    // GETTERS
    // ==============================
    public List<TemporaryContractPermit> getById_contract(Integer id){
        List<TemporaryContractPermit> list = temporarycontractpermitCrud.findById_contract(id);
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontraron registros para el contrato");
        return list;
    }

    public ResponseSuccessfullyDto getById_contractResponse(Integer id){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getById_contract(id))
                .build();
    }

    public List<TemporaryContractPermit> getById_assigned(Integer id){
        List<TemporaryContractPermit> list = temporarycontractpermitCrud.findById_assigned(id);
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontraron registros para el encargado");
        return list;
    }

    public ResponseSuccessfullyDto getById_assignedResponse(Integer id){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getById_assigned(id))
                .build();
    }

    public List<TemporaryContractPermit> getAllTemporaryContractPermitList(){
        List<TemporaryContractPermit> list = temporarycontractpermitCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No hay registros");
        return list;
    }

    public ResponseSuccessfullyDto getAllTemporaryContractPermitListResponse(){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getAllTemporaryContractPermitList())
                .build();
    }

    public TemporaryContractPermit getTemporaryContractPermitById(Integer id){
        return temporarycontractpermitCrud.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Registro no encontrado"));
    }

    public ResponseSuccessfullyDto getTemporaryContractPermitByIdResponse(Integer id){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registro encontrado con Éxito")
                .body(getTemporaryContractPermitById(id))
                .build();
    }

    // ==============================
    // CRUD
    // ==============================
    public ResponseSuccessfullyDto createTemporaryContractPermit(NewTemporaryContractPermitDto dto){
        TemporaryContractPermit e = new TemporaryContractPermit();
        e.setContract(contractService.getContractById(dto.getId_contract()));
        e.setTemporary_plate(dto.getTemporary_plate());
        e.setStart_date(dto.getStart_date());
        e.setEnd_date(dto.getEnd_date());
        e.setMax_uses(dto.getMax_uses());
        e.setUsed_count(dto.getUsed_count());
        e.setRemaining_count(dto.getRemaining_count());
        e.setIs_4r(dto.getIs_4r());
        e.setStatus(dto.getStatus());
        e.setObservations(dto.getObservations());
        e.setEncargado(userCrud.findById(dto.getId_assigned()).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Usuario asignado no encontrado")));

        temporarycontractpermitCrud.save(e);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Registro creado con Éxito")
                .build();
    }

    public ResponseSuccessfullyDto updateTemporaryContractPermit(TemporaryContractPermitDto dto){
        TemporaryContractPermit existing = getTemporaryContractPermitById(dto.getId());
        existing.setContract(contractService.getContractById(dto.getId_contract()));
        existing.setTemporary_plate(dto.getTemporary_plate());
        existing.setStart_date(dto.getStart_date());
        existing.setEnd_date(dto.getEnd_date());
        existing.setMax_uses(dto.getMax_uses());
        existing.setUsed_count(dto.getUsed_count());
        existing.setRemaining_count(dto.getRemaining_count());
        existing.setIs_4r(dto.getIs_4r());
        existing.setStatus(dto.getStatus());
        existing.setObservations(dto.getObservations());
        existing.setEncargado(userCrud.findById(dto.getId_assigned()).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Usuario asignado no encontrado")));

        temporarycontractpermitCrud.save(existing);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Registro actualizado con Éxito")
                .build();
    }

    public ResponseSuccessfullyDto deleteTemporaryContractPermit(Integer id){
        TemporaryContractPermit entity = getTemporaryContractPermitById(id);
        temporarycontractpermitCrud.delete(entity);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Registro eliminado con Éxito")
                .build();
    }
}
