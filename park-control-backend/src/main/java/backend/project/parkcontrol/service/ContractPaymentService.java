package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewContractPaymentDto;
import backend.project.parkcontrol.dto.response.ContractPaymentDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.ContractPaymentCrud;
import backend.project.parkcontrol.repository.entities.ContractPayment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContractPaymentService {
    private final ContractPaymentCrud contractpaymentCrud;
    private final ContractService contractService;
    // FK helper: find by id_contract
    public List<ContractPayment> getById_contract(Integer id){
        List<ContractPayment> list = contractpaymentCrud.findById_contract(id);
        if(list.isEmpty()) throw new BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    public List<ContractPayment> getAllContractPaymentList(){
        List<ContractPayment> list = contractpaymentCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No records");
        return list;
    }

    public ContractPayment getContractPaymentById(Integer id){
        Optional<ContractPayment> optional = contractpaymentCrud.findById(id);
        if(optional.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "ContractPayment not found");
        return optional.get();
    }

    public void deleteContractPayment(Integer id){
        ContractPayment entity = getContractPaymentById(id);
        contractpaymentCrud.delete(entity);
    }

    public void createContractPayment(NewContractPaymentDto dto){
        ContractPayment e = new ContractPayment();
        e.setContract(contractService.getContractById(dto.getId_contract()));
        e.setSubtotal(dto.getSubtotal());
        e.setMonthly_discount(dto.getMonthly_discount());
        e.setAnnual_discount(dto.getAnnual_discount());
        e.setTotal(dto.getTotal());
        e.setDate(dto.getDate());
        e.setPayment_method(dto.getPayment_method());

        contractpaymentCrud.save(e);
    }

    public void updateContractPayment(ContractPaymentDto dto){
        ContractPayment existing = getContractPaymentById(dto.getId());
        existing.setContract(contractService.getContractById(dto.getId_contract()));
        existing.setSubtotal(dto.getSubtotal());
        existing.setMonthly_discount(dto.getMonthly_discount());
        existing.setAnnual_discount(dto.getAnnual_discount());
        existing.setTotal(dto.getTotal());
        existing.setDate(dto.getDate());
        existing.setPayment_method(dto.getPayment_method());

        contractpaymentCrud.save(existing);
    }
}
