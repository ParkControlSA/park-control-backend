package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewContractPaymentDto;
import backend.project.parkcontrol.dto.response.ContractPaymentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.ContractPaymentCrud;
import backend.project.parkcontrol.repository.entities.Contract;
import backend.project.parkcontrol.repository.entities.ContractPayment;
import backend.project.parkcontrol.repository.entities.RateAssignment;
import backend.project.parkcontrol.repository.entities.SubscriptionPlan;
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
public class ContractPaymentService {
    private final ContractPaymentCrud contractpaymentCrud;
    private final ContractService contractService;
    private final RateAssignmentService rateAssignmentService;
    private final SubscriptionPlanService subscriptionPlanService;
    private static final Integer ID_SUCURSAL_BASE = 1;
    private static final Integer MONTHS_ANUAL_PLAN = 12;
    // ==============================
    // GETTERS
    // ==============================

    public List<ContractPayment> getById_contract(Integer id) {
        List<ContractPayment> list = contractpaymentCrud.findById_contract(id);
        return list;
    }

    public List<ContractPayment> getAllContractPaymentList() {
        List<ContractPayment> list = contractpaymentCrud.findAll();
        return list;
    }

    public ContractPayment getContractPaymentById(Integer id) {
        Optional<ContractPayment> optional = contractpaymentCrud.findById(id);
        return optional.get();
    }

    // ==============================
    // CRUD Methods with ResponseSuccessfullyDto
    // ==============================

    public ResponseSuccessfullyDto deleteContractPayment(Integer id) {
        ContractPayment entity = getContractPaymentById(id);
        contractpaymentCrud.delete(entity);
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro eliminado con Éxito")
                .build();
    }

    public ResponseSuccessfullyDto createContractPayment(NewContractPaymentDto dto) {
        ContractPayment e = new ContractPayment();
        Contract contract = contractService.getContractById(dto.getId_contract());
        RateAssignment rateAssignment = rateAssignmentService.getRateAssignamentById_branchIsActive(ID_SUCURSAL_BASE).getFirst();
        SubscriptionPlan subscriptionPlan = contract.getSubscriptionPlan();
        Double pagoSinDescuentos = contract.getMonths()*rateAssignment.getHourly_rate();
        Double descuentoMensual = pagoSinDescuentos*(subscriptionPlan.getTotal_discount()/100);
        e.setContract(contract);
        e.setSubtotal(pagoSinDescuentos);
        e.setMonthly_discount(descuentoMensual);
        if (contract.getIs_anual()) {
            Double descuentoAnual = pagoSinDescuentos*((subscriptionPlan.getAnnual_discount()/MONTHS_ANUAL_PLAN)/100);
            e.setAnnual_discount(descuentoAnual);
            e.setTotal(pagoSinDescuentos-descuentoMensual-descuentoAnual);
        }else {
            e.setAnnual_discount(0.0);
            e.setTotal(pagoSinDescuentos-descuentoMensual);
        }
        e.setDate(LocalDateTime.now(ZoneId.of("America/Guatemala")));
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
                .code(HttpStatus.OK)
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
