package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.ContractApi;
import backend.project.parkcontrol.dto.request.NewContractDto;
import backend.project.parkcontrol.dto.response.ContractDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ContractController implements ContractApi {

    private final ContractService contractService;

    // ==============================
    // CRUD
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createContract(NewContractDto dto) {
        log.info("POST /contract");
        ResponseSuccessfullyDto resp = contractService.createContract(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateContract(ContractDto dto) {
        log.info("PUT /contract");
        ResponseSuccessfullyDto resp = contractService.updateContract(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteContract(Integer id) {
        log.info("DELETE /contract/{}", id);
        ResponseSuccessfullyDto resp = contractService.deleteContract(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    // ==============================
    // GETTERS
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllContracts() {
        log.info("GET /contract/all");
        ResponseSuccessfullyDto resp = contractService.getAllContractListResponse();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getContractById(Integer id) {
        log.info("GET /contract/{}", id);
        ResponseSuccessfullyDto resp = contractService.getContract(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getContractsByUserId(Integer id) {
        log.info("GET /contract/user/{}", id);
        ResponseSuccessfullyDto resp = contractService.getById_userResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getContractsByPlate(String plate) {
        log.info("GET /contract/plate/{}", plate);
        ResponseSuccessfullyDto resp = contractService.getByLicense_plateResponse(plate);
        return new ResponseEntity<>(resp, resp.getCode());
    }
}
