package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewContractDto;
import backend.project.parkcontrol.dto.request.NewContractHistoryDto;
import backend.project.parkcontrol.dto.response.ContractDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.ContractCrud;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.Contract;
import backend.project.parkcontrol.repository.entities.ContractHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContractService {
    private final ContractCrud contractCrud;
    private final UserCrud userCrud;
    private final SubscriptionPlanService subscriptionPlanService;
    private static final Integer CONTRACT_LIMIT_MONTHS = 12;
    private final ValidationService validationService;
    private final ContractHistoryService contractHistoryService;

    // ==============================
    // GETTERS
    // ==============================

    public List<Contract> getById_user(Integer id) {
        List<Contract> list = contractCrud.findById_user(id);
        return list;
    }

    public List<Contract> getByLicense_plate(String plate) {
        List<Contract> list = contractCrud.findByLicense_plate(plate);
        return list;
    }

    public List<Contract> getByLicense_plateIsActive(String plate) {
        List<Contract> list = contractCrud.findByLicense_plateActive(plate, true);
        return list;
    }

    public List<Contract> getAllContractList() {
        List<Contract> list = contractCrud.findAll();
        return list;
    }

    public Contract getContractById(Integer id) {
        Optional<Contract> optional = contractCrud.findById(id);       
        return optional.get();
    }

    // ==============================
    // CRUD Methods with ResponseSuccessfullyDto
    // ==============================

    public ResponseSuccessfullyDto deleteContract(Integer id) {
        Contract entity = getContractById(id);
        contractCrud.delete(entity);
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro eliminado con Éxito")
                .build();
    }

    public ResponseSuccessfullyDto createContract(NewContractDto dto) {
        Contract e = new Contract();
        e.setUser(userCrud.findById(dto.getId_user()).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "User not found")));
        e.setSubscriptionPlan(subscriptionPlanService.getSubscriptionPlanById(dto.getId_plan()));
        e.setIs_4r(dto.getIs_4r());
        verifyPlate(dto.getLicense_plate());
        e.setLicense_plate(dto.getLicense_plate());
        LocalDateTime hoy = LocalDateTime.now();
        e.setStart_date(hoy);
        e.setEnd_date(hoy.plusMonths(dto.getMonths()));
        verifyMonths(dto.getMonths());
        e.setMonths(dto.getMonths());
        e.setIs_anual(dto.getMonths() == CONTRACT_LIMIT_MONTHS);
        e.setActive(Boolean.TRUE);
        Contract saved = contractCrud.save(e);
        //Crea los historiales del contrato
        contractHistoryService.createContractHistory(saved);
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .body(Map.of("id", saved.getId()))
                .message("Registro creado con Éxito")
                .build();
    }

    private void verifyPlate(String licensePlate) {
        if (!getByLicense_plate(licensePlate).isEmpty()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "La placa ya está registrada en otro contrato.");
        }
    }

    private void verifyMonths(Integer months) {
        validationService.validatePositiveNumber(months, "meses");
        if (months>CONTRACT_LIMIT_MONTHS){
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "Solamente es posible adquirir un contrato anualmente (12 meses)");
        }
    }

    public ResponseSuccessfullyDto updateContract(ContractDto dto) {
        Contract existing = getContractById(dto.getId());
        existing.setUser(userCrud.findById(dto.getId_user()).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "User not found")));
        existing.setSubscriptionPlan(subscriptionPlanService.getSubscriptionPlanById(dto.getId_plan()));
        existing.setIs_4r(dto.getIs_4r());
        existing.setLicense_plate(dto.getLicense_plate());
        existing.setStart_date(dto.getStart_date());
        existing.setEnd_date(dto.getEnd_date());
        existing.setMonths(dto.getMonths());
        existing.setIs_anual(dto.getIs_anual());
        existing.setActive(dto.getActive());
        contractCrud.save(existing);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro actualizado con Éxito")
                .build();
    }

    // ==============================
    // ResponseSuccessfullyDto Getters
    // ==============================

    public ResponseSuccessfullyDto getContract(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registro encontrado con Éxito")
                .body(getContractById(id))
                .build();
    }

    public ResponseSuccessfullyDto getAllContractListResponse() {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getAllContractList())
                .build();
    }

    public ResponseSuccessfullyDto getById_userResponse(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getById_user(id))
                .build();
    }

    public ResponseSuccessfullyDto getByLicense_plateResponse(String id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getByLicense_plate(id))
                .build();
    }


}
