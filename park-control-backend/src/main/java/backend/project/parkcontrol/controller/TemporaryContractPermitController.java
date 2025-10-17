package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.TemporaryContractPermitApi;
import backend.project.parkcontrol.dto.request.NewTemporaryContractPermitDto;
import backend.project.parkcontrol.dto.response.TemporaryContractPermitDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.TemporaryContractPermitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TemporaryContractPermitController implements TemporaryContractPermitApi {

    private final TemporaryContractPermitService service;

    // ==============================
    // CRUD
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> create(NewTemporaryContractPermitDto dto) {
        log.info("POST /temporaryContractPermit");
        ResponseSuccessfullyDto resp = service.createTemporaryContractPermit(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> update(TemporaryContractPermitDto dto) {
        log.info("PUT /temporaryContractPermit");
        ResponseSuccessfullyDto resp = service.updateTemporaryContractPermit(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> delete(Integer id) {
        log.info("DELETE /temporaryContractPermit/{}", id);
        ResponseSuccessfullyDto resp = service.deleteTemporaryContractPermit(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    // ==============================
    // GETTERS
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAll() {
        log.info("GET /temporaryContractPermit/all");
        ResponseSuccessfullyDto resp = service.getAllTemporaryContractPermitListResponse();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getById(Integer id) {
        log.info("GET /temporaryContractPermit/{}", id);
        ResponseSuccessfullyDto resp = service.getTemporaryContractPermitByIdResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getByContract(Integer id) {
        log.info("GET /temporaryContractPermit/contract/{}", id);
        ResponseSuccessfullyDto resp = service.getById_contractResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getByAssigned(Integer id) {
        log.info("GET /temporaryContractPermit/assigned/{}", id);
        ResponseSuccessfullyDto resp = service.getById_assignedResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }
}
