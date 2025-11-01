package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewLicensePlateBlockRequestDto;
import backend.project.parkcontrol.dto.response.LicensePlateBlockRequestDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.enums.LicensePlateBlockRequestStatus;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.ContractCrud;
import backend.project.parkcontrol.repository.crud.LicensePlateBlockRequestCrud;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.Contract;
import backend.project.parkcontrol.repository.entities.LicensePlateBlockRequest;
import backend.project.parkcontrol.repository.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class LicensePlateBlockRequestService {

    private final LicensePlateBlockRequestCrud licensePlateBlockRequestCrud;
    private final ContractService contractService;
    private final ContractCrud contractCrud;
    private final UserCrud userCrud;

    // ==============================
    // GETTERS
    // ==============================

    public List<LicensePlateBlockRequest> getById_contract(Integer id) {
        List<LicensePlateBlockRequest> list = licensePlateBlockRequestCrud.findById_contract(id);
        return list;
    }

    public List<LicensePlateBlockRequest> getById_assigned(Integer id) {
        List<LicensePlateBlockRequest> list = licensePlateBlockRequestCrud.findById_assigned(id);
        return list;
    }

    public List<LicensePlateBlockRequest> getAllLicensePlateBlockRequestList() {
        List<LicensePlateBlockRequest> list = licensePlateBlockRequestCrud.findAll();
        return list;
    }

    public List<LicensePlateBlockRequest> getAllLicensePlateBlockRequestListByStatus(Integer id) {
        List<LicensePlateBlockRequest> list = licensePlateBlockRequestCrud.findAllByStatus(id);
        return list;
    }

    public LicensePlateBlockRequest getLicensePlateBlockRequestById(Integer id) {
        return licensePlateBlockRequestCrud.findById(id).get();
    }

    // ==============================
    // CRUD Methods
    // ==============================

    public ResponseSuccessfullyDto createLicensePlateBlockRequest(NewLicensePlateBlockRequestDto dto) {
        verifyDto(dto);
        LicensePlateBlockRequest e = new LicensePlateBlockRequest();
        e.setIs_4r(dto.getIs_4r());
        Contract contract = contractService.getContractById(dto.getId_contract());
        e.setContract(contract);
        e.setUser(userCrud.findById(dto.getId_assigned())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Usuario asignado no encontrado")));
        e.setOld_plate(contract.getLicense_plate());
        e.setNew_plate(dto.getNew_plate());
        e.setEvidence_url(dto.getEvidence_url());
        e.setNote(dto.getNote());
        e.setCreation_date(LocalDateTime.now(ZoneId.of("America/Guatemala")));
        e.setStatus(LicensePlateBlockRequestStatus.PENDIENTE.getValue());

        licensePlateBlockRequestCrud.save(e);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Registro creado con Éxito")
                .build();
    }

    private void verifyDto(NewLicensePlateBlockRequestDto dto) {
        List<LicensePlateBlockRequest> licensePlateBlockRequest = licensePlateBlockRequestCrud.findById_contract(dto.getId_contract());
        if (!licensePlateBlockRequest.isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "Ha hecho una solicitud de cambio de placa recientemente, debe esperar 12 meses para realizar una nueva petición");
        }

        Contract contract = contractService.getContractById(dto.getId_contract());
        if (!contract.getActive()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "El contrato no se encuentra activo");
        }

        if (!contractService.getByLicense_plateIsActive(dto.getNew_plate()).isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "La nueva placa ya está asociada a un contrato activo.");
        }

        UserEntity userEntity = userCrud.findById(dto.getId_assigned()).get();
        if (userEntity.getRol().getId() != 3) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "El usuario no corresponde a un Back Office");
        }
    }


    public ResponseSuccessfullyDto changeStatus(Integer idLicensePlateBlockRequest, Integer status) {
        LicensePlateBlockRequest existing = getLicensePlateBlockRequestById(idLicensePlateBlockRequest);
        String message = "";
        switch (status){
            case 1:
                throw new BusinessException(HttpStatus.BAD_REQUEST,
                        "No es posible regresar al estado pendiente.");
            case 2:
                Contract contract = existing.getContract();
                contract.setLicense_plate(existing.getNew_plate());
                contract.setIs_4r(existing.getIs_4r());
                contractCrud.save(contract);
                message = "El Cambio de Placa se ha realizado correctamente.";
                break;
            case 3:
                message = "El Cambio de Placa ha sido rechazado.";
                break;
            case 4:
                Contract contract1 = existing.getContract();
                contract1.setLicense_plate(existing.getOld_plate());
                contract1.setIs_4r(existing.getIs_4r());
                contractCrud.save(contract1);
                message = "El Cambio de Placa ha sido revocado, se ha restaurado la placa original.";
                break;
            default:
                throw new BusinessException(HttpStatus.BAD_REQUEST,
                        "No existe el estado ingresado.");
        }
        existing.setStatus(status);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message(message)
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

    public ResponseSuccessfullyDto getAllLicensePlateBlockRequestListByStatusResponse(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getAllLicensePlateBlockRequestListByStatus(id))
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
