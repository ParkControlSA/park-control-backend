package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.BranchTemporaryPermitApi;
import backend.project.parkcontrol.dto.request.NewBranchTemporaryPermitDto;
import backend.project.parkcontrol.dto.response.BranchTemporaryPermitDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.BranchTemporaryPermitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BranchTemporaryPermitController implements BranchTemporaryPermitApi {

    private final BranchTemporaryPermitService branchTemporaryPermitervice;

    // ==============================
    // CRUD
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createBranchTemporaryPermit(NewBranchTemporaryPermitDto dto) {
        log.info("POST /branchTemporaryPermit");
        ResponseSuccessfullyDto resp = branchTemporaryPermitervice.createBranchTemporaryPermit(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateBranchTemporaryPermit(BranchTemporaryPermitDto dto) {
        log.info("PUT /branchTemporaryPermit");
        ResponseSuccessfullyDto resp = branchTemporaryPermitervice.updateBranchTemporaryPermit(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteBranchTemporaryPermit(Integer id) {
        log.info("DELETE /branchTemporaryPermit/{}", id);
        ResponseSuccessfullyDto resp = branchTemporaryPermitervice.deleteBranchTemporaryPermit(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    // ==============================
    // GETTERS
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllbranchTemporaryPermit() {
        log.info("GET /branchTemporaryPermit/all");
        ResponseSuccessfullyDto resp = branchTemporaryPermitervice.getAllBranchTemporaryPermitListResponse();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getBranchTemporaryPermitById(Integer id) {
        log.info("GET /branchTemporaryPermit/{}", id);
        ResponseSuccessfullyDto resp = branchTemporaryPermitervice.getBranchTemporaryPermit(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getByIdBranch(Integer id) {
        log.info("GET /branchTemporaryPermit/branch/{}", id);
        ResponseSuccessfullyDto resp = branchTemporaryPermitervice.getById_branchResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getByIdTemporaryPermit(Integer id) {
        log.info("GET /branchTemporaryPermit/temporaryPermit/{}", id);
        ResponseSuccessfullyDto resp = branchTemporaryPermitervice.getById_temporary_permitResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }
}
