package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewLicensePlateBlockRequestDto;
import backend.project.parkcontrol.dto.response.LicensePlateBlockRequestDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.LicensePlateBlockRequestCrud;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.LicensePlateBlockRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class LicensePlateBlockRequestService {

    private final LicensePlateBlockRequestCrud licensePlateBlockRequestCrud;
    private final ContractService contractService;
    private final UserCrud userCrud;

    // ==============================
    // GETTERS
    // ==============================

    public List<LicensePlateBlockRequest> getById_contract(Integer id) {
        List<LicensePlateBlockRequest> list = licensePlateBlockRequestCrud.findById_contract(id);
        if (list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontraron registros para el contrato");
        return list;
    }

    public List<LicensePlateBlockRequest> getById_assigned(Integer id) {
        List<LicensePlateBlockRequest> list = licensePlateBlockRequestCrud.findById_assigned(id);
        if (list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontraron registros para el encargado");
        return list;
    }

    public List<LicensePlateBlockRequest> getAllLicensePlateBlockRequestList() {
        List<LicensePlateBlockRequest> list = licensePlateBlockRequestCrud.findAll();
        if (list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No hay registros");
        return list;
    }

    public LicensePlateBlockRequest getLicensePlateBlockRequestById(Integer id) {
        return licensePlateBlockRequestCrud.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Solicitud de bloqueo no encontrada"));
    }

    // ==============================
    // CRUD Methods
    // ==============================

    public ResponseSuccessfullyDto createLicensePlateBlockRequest(NewLicensePlateBlockRequestDto dto) {
        LicensePlateBlockRequest e = new LicensePlateBlockRequest();
        e.setIs_4r(dto.getIs_4r());
        e.setContract(contractService.getContractById(dto.getId_contract()));
        e.setUser(userCrud.findById(dto.getId_assigned())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Usuario asignado no encontrado")));
        e.setOld_plate(dto.getOld_plate());
        e.setNew_plate(dto.getNew_plate());
        e.setEvidence_url(dto.getEvidence_url());
        e.setNote(dto.getNote());
        e.setCreation_date(dto.getCreation_date());
        e.setStatus(dto.getStatus());

        licensePlateBlockRequestCrud.save(e);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Registro creado con Éxito")
                .build();
    }

    public ResponseSuccessfullyDto updateLicensePlateBlockRequest(LicensePlateBlockRequestDto dto) {
        LicensePlateBlockRequest existing = getLicensePlateBlockRequestById(dto.getId());
        existing.setIs_4r(dto.getIs_4r());
        existing.setContract(contractService.getContractById(dto.getId_contract()));
        existing.setUser(userCrud.findById(dto.getId_assigned())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Usuario asignado no encontrado")));
        existing.setOld_plate(dto.getOld_plate());
        existing.setNew_plate(dto.getNew_plate());
        existing.setEvidence_url(dto.getEvidence_url());
        existing.setNote(dto.getNote());
        existing.setCreation_date(dto.getCreation_date());
        existing.setStatus(dto.getStatus());

        licensePlateBlockRequestCrud.save(existing);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro actualizado con Éxito")
                .build();
    }

    public ResponseSuccessfullyDto deleteLicensePlateBlockRequest(Integer id) {
        LicensePlateBlockRequest entity = getLicensePlateBlockRequestById(id);
        licensePlateBlockRequestCrud.delete(entity);
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro eliminado con Éxito")
                .build();
    }

    // ==============================
    // ResponseSuccessfullyDto Getters
    // ==============================

    public ResponseSuccessfullyDto getLicensePlateBlockRequest(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registro encontrado con Éxito")
                .body(getLicensePlateBlockRequestById(id))
                .build();
    }

    public ResponseSuccessfullyDto getAllLicensePlateBlockRequestListResponse() {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getAllLicensePlateBlockRequestList())
                .build();
    }

    public ResponseSuccessfullyDto getById_contractResponse(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getById_contract(id))
                .build();
    }

    public ResponseSuccessfullyDto getById_assignedResponse(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getById_assigned(id))
                .build();
    }
}
