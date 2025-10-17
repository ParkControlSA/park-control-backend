package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewContractHistoryDto;
import backend.project.parkcontrol.dto.response.ContractHistoryDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.ContractHistoryCrud;
import backend.project.parkcontrol.repository.entities.ContractHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContractHistoryService {
    private final ContractHistoryCrud contracthistoryCrud;
    private final ContractService contractService;
    // FK helper: find by id_contract
    public List<ContractHistory> getById_contract(Integer id){
        List<ContractHistory> list = contracthistoryCrud.findById_contract(id);
        if(list.isEmpty()) throw new backend.project.parkcontrol.exception.BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    public List<ContractHistory> getAllContractHistoryList(){
        List<ContractHistory> list = contracthistoryCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No records");
        return list;
    }

    public ContractHistory getContractHistoryById(Integer id){
        Optional<ContractHistory> optional = contracthistoryCrud.findById(id);
        if(optional.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "ContractHistory not found");
        return optional.get();
    }

    public void deleteContractHistory(Integer id){
        ContractHistory entity = getContractHistoryById(id);
        contracthistoryCrud.delete(entity);
    }

    public void createContractHistory(NewContractHistoryDto dto){
        ContractHistory e = new ContractHistory();
        e.setContract(contractService.getContractById(dto.getId_contract()));
        e.setIncluded_hours(dto.getIncluded_hours());
        e.setConsumed_hours(dto.getConsumed_hours());
        e.setDate(dto.getDate());

        contracthistoryCrud.save(e);
    }

    public void updateContractHistory(ContractHistoryDto dto){
        ContractHistory existing = getContractHistoryById(dto.getId());
        existing.setContract(contractService.getContractById(dto.getId_contract()));
        existing.setIncluded_hours(dto.getIncluded_hours());
        existing.setConsumed_hours(dto.getConsumed_hours());
        existing.setDate(dto.getDate());

        contracthistoryCrud.save(existing);
    }
}
