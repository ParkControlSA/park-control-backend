package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.ContractPaymentApi;
import backend.project.parkcontrol.dto.request.NewContractPaymentDto;
import backend.project.parkcontrol.dto.response.ContractPaymentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.ContractPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ContractPaymentController implements ContractPaymentApi {

    private final ContractPaymentService contractPaymentService;

    // ==============================
    // CRUD
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createContractPayment(NewContractPaymentDto dto) {
        log.info("POST /contractPayment");
        ResponseSuccessfullyDto resp = contractPaymentService.createContractPayment(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateContractPayment(ContractPaymentDto dto) {
        log.info("PUT /contractPayment");
        ResponseSuccessfullyDto resp = contractPaymentService.updateContractPayment(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteContractPayment(Integer id) {
        log.info("DELETE /contractPayment/{}", id);
        ResponseSuccessfullyDto resp = contractPaymentService.deleteContractPayment(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    // ==============================
    // GETTERS
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllContractPayments() {
        log.info("GET /contractPayment/all");
        ResponseSuccessfullyDto resp = contractPaymentService.getAllContractPaymentListResponse();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getContractPaymentById(Integer id) {
        log.info("GET /contractPayment/{}", id);
        ResponseSuccessfullyDto resp = contractPaymentService.getContractPayment(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getContractPaymentsByContractId(Integer id) {
        log.info("GET /contractPayment/contract/{}", id);
        ResponseSuccessfullyDto resp = contractPaymentService.getById_contractResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }
}
