package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewContractPaymentDto;
import backend.project.parkcontrol.dto.response.ContractPaymentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
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

    // ==============================
    // GETTERS
    // ==============================

    public List<ContractPayment> getById_contract(Integer id) {
        List<ContractPayment> list = contractpaymentCrud.findById_contract(id);
        if (list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    public List<ContractPayment> getAllContractPaymentList() {
        List<ContractPayment> list = contractpaymentCrud.findAll();
        if (list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No records");
        return list;
    }

    public ContractPayment getContractPaymentById(Integer id) {
        Optional<ContractPayment> optional = contractpaymentCrud.findById(id);
        if (optional.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "ContractPayment not found");
        return optional.get();
    }

    // ==============================
    // CRUD Methods with ResponseSuccessfullyDto
    // ==============================

    public ResponseSuccessfullyDto deleteContractPayment(Integer id) {
        ContractPayment entity = getContractPaymentById(id);
        contractpaymentCrud.delete(entity);
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Registro eliminado con Éxito")
                .build();
    }

    public ResponseSuccessfullyDto createContractPayment(NewContractPaymentDto dto) {
        ContractPayment e = new ContractPayment();
        e.setContract(contractService.getContractById(dto.getId_contract()));
        e.setSubtotal(dto.getSubtotal());
        e.setMonthly_discount(dto.getMonthly_discount());
        e.setAnnual_discount(dto.getAnnual_discount());
        e.setTotal(dto.getTotal());
        e.setDate(dto.getDate());
        e.setPayment_method(dto.getPayment_method());
        contractpaymentCrud.save(e);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Registro creado con Éxito")
                .build();
    }

    public ResponseSuccessfullyDto updateContractPayment(ContractPaymentDto dto) {
        ContractPayment existing = getContractPaymentById(dto.getId());
        existing.setContract(contractService.getContractById(dto.getId_contract()));
        existing.setSubtotal(dto.getSubtotal());
        existing.setMonthly_discount(dto.getMonthly_discount());
        existing.setAnnual_discount(dto.getAnnual_discount());
        existing.setTotal(dto.getTotal());
        existing.setDate(dto.getDate());
        existing.setPayment_method(dto.getPayment_method());
        contractpaymentCrud.save(existing);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Registro actualizado con Éxito")
                .build();
    }

    // ==============================
    // ResponseSuccessfullyDto Getters
    // ==============================

    public ResponseSuccessfullyDto getContractPayment(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registro encontrado con Éxito")
                .body(getContractPaymentById(id))
                .build();
    }

    public ResponseSuccessfullyDto getAllContractPaymentListResponse() {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getAllContractPaymentList())
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
