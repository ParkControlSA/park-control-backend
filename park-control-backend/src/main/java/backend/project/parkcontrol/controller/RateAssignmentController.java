package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.RateAssignmentApi;
import backend.project.parkcontrol.dto.request.NewRateAssignmentDto;
import backend.project.parkcontrol.dto.response.RateAssignmentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.RateAssignmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RateAssignmentController implements RateAssignmentApi {

    private final RateAssignmentService service;

    // ==============================
    // CRUD
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> create(NewRateAssignmentDto dto) {
        log.info("POST /rateAssignment");
        ResponseSuccessfullyDto resp = service.createRateAssignment(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> update(RateAssignmentDto dto) {
        log.info("PUT /rateAssignment");
        ResponseSuccessfullyDto resp = service.updateRateAssignment(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> delete(Integer id) {
        log.info("DELETE /rateAssignment/{}", id);
        ResponseSuccessfullyDto resp = service.deleteRateAssignment(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    // ==============================
    // GETTERS
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAll() {
        log.info("GET /rateAssignment/all");
        ResponseSuccessfullyDto resp = service.getAllRateAssignmentListResponse();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getById(Integer id) {
        log.info("GET /rateAssignment/{}", id);
        ResponseSuccessfullyDto resp = service.getRateAssignment(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getByBranchId(Integer id) {
        log.info("GET /rateAssignment/branch/{}", id);
        ResponseSuccessfullyDto resp = service.getById_branchResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }
}
