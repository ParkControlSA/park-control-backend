package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewContractHistoryDto;
import backend.project.parkcontrol.dto.response.ContractHistoryDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.ContractHistoryCrud;
import backend.project.parkcontrol.repository.entities.Contract;
import backend.project.parkcontrol.repository.entities.ContractHistory;
import backend.project.parkcontrol.repository.entities.SubscriptionPlan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContractHistoryService {
    private final ContractHistoryCrud contracthistoryCrud;
    //private final ContractService contractService;

    // ==============================
    // GETTERS
    // ==============================

    public List<ContractHistory> getById_contract(Integer id) {
        List<ContractHistory> list = contracthistoryCrud.findById_contract(id);
        return list;
    }

    public List<ContractHistory> getAllContractHistoryList() {
        List<ContractHistory> list = contracthistoryCrud.findAll();
        return list;
    }

    public ContractHistory getContractHistoryById(Integer id) {
        Optional<ContractHistory> optional = contracthistoryCrud.findById(id);
        return optional.get();
    }

    // ==============================
    // CRUD Methods with ResponseSuccessfullyDto
    // ==============================

    public ResponseSuccessfullyDto deleteContractHistory(Integer id) {
        ContractHistory entity = getContractHistoryById(id);
        contracthistoryCrud.delete(entity);
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro eliminado con Éxito")
                .build();
    }

    public ResponseSuccessfullyDto createContractHistory(Contract contract) {
        verifyPlan(contract);
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Registros creados con Éxito")
                .build();
    }

    private void verifyPlan(Contract contract) {
        LocalDateTime today = LocalDateTime.now(ZoneId.of("America/Guatemala"));
        LocalDateTime limitDay = today.plusMonths(contract.getMonths());
        SubscriptionPlan subscriptionPlan = contract.getSubscriptionPlan();
        if (subscriptionPlan.getId().equals(2) || subscriptionPlan.getId().equals(5)){
            for (LocalDateTime fecha = today; fecha.isBefore(limitDay); fecha = fecha.plusDays(1)) {
                saveContractHistory(contract, fecha);
            }
        } else if (subscriptionPlan.getId().equals(3) || subscriptionPlan.getId().equals(4)) {
            for (LocalDateTime fecha = today; fecha.isBefore(limitDay); fecha = fecha.plusDays(1)) {
                DayOfWeek dia = fecha.getDayOfWeek();
                if (dia != DayOfWeek.SATURDAY && dia != DayOfWeek.SUNDAY){
                    saveContractHistory(contract, fecha);
                }
            }
        }else{
            //LOGICA PLAN NOCTURNO
        }
    }

    private void saveContractHistory(Contract contract, LocalDateTime fecha) {
        ContractHistory e = new ContractHistory();
        e.setContract(contract);
        e.setIncluded_hours(contract.getSubscriptionPlan().getDaily_hours());
        e.setConsumed_hours(0);
        e.setDate(fecha);
        contracthistoryCrud.save(e);
        log.info("ContractHistory del "+ fecha +" guardada.");
    }


    public ResponseSuccessfullyDto updateContractHistory(ContractHistoryDto dto) {
        ContractHistory existing = getContractHistoryById(dto.getId());
        //existing.setContract(contractService.getContractById(dto.getId_contract()));
        existing.setIncluded_hours(dto.getIncluded_hours());
        existing.setConsumed_hours(dto.getConsumed_hours());
        existing.setDate(dto.getDate());
        contracthistoryCrud.save(existing);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro actualizado con Éxito")
                .build();
    }

    // ==============================
    // ResponseSuccessfullyDto Getters
    // ==============================

    public ResponseSuccessfullyDto getContractHistory(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registro encontrado con Éxito")
                .body(getContractHistoryById(id))
                .build();
    }

    public ResponseSuccessfullyDto getAllContractHistoryListResponse() {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getAllContractHistoryList())
                .build();
    }

    public ResponseSuccessfullyDto getById_contractResponse(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getById_contract(id))
                .build();
    }
}
