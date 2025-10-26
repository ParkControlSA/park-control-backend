package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.ContractHistoryApi;
import backend.project.parkcontrol.dto.request.NewContractHistoryDto;
import backend.project.parkcontrol.dto.response.ContractHistoryDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.ContractHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ContractHistoryController implements ContractHistoryApi {

    private final ContractHistoryService contractHistoryService;

    // ==============================
    // CRUD
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createContractHistory(NewContractHistoryDto dto) {
        log.info("POST /contractHistory");
        ResponseSuccessfullyDto resp = contractHistoryService.createContractHistory(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateContractHistory(ContractHistoryDto dto) {
        log.info("PUT /contractHistory");
        ResponseSuccessfullyDto resp = contractHistoryService.updateContractHistory(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteContractHistory(Integer id) {
        log.info("DELETE /contractHistory/{}", id);
        ResponseSuccessfullyDto resp = contractHistoryService.deleteContractHistory(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    // ==============================
    // GETTERS
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllContractHistorys() {
        log.info("GET /contractHistory/all");
        ResponseSuccessfullyDto resp = contractHistoryService.getAllContractHistoryListResponse();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getContractHistoryById(Integer id) {
        log.info("GET /contractHistory/{}", id);
        ResponseSuccessfullyDto resp = contractHistoryService.getContractHistory(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getContractHistoryByContractId(Integer id) {
        log.info("GET /contractHistory/contract/{}", id);
        ResponseSuccessfullyDto resp = contractHistoryService.getById_contractResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }
}
