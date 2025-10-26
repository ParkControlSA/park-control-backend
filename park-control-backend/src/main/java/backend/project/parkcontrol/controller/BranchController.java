package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.BranchApi;
import backend.project.parkcontrol.dto.request.NewBranchDto;
import backend.project.parkcontrol.dto.response.BranchDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BranchController implements BranchApi {

    private final BranchService branchService;

    // ==============================
    // CRUD
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createBranch(NewBranchDto dto) {
        log.info("POST /branch");
        ResponseSuccessfullyDto resp = branchService.createBranch(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateBranch(BranchDto dto) {
        log.info("PUT /branch");
        ResponseSuccessfullyDto resp = branchService.updateBranch(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteBranch(Integer id) {
        log.info("DELETE /branch/{}", id);
        ResponseSuccessfullyDto resp = branchService.deleteBranch(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    // ==============================
    // GETTERS
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllbranch() {
        log.info("GET /branch/all");
        ResponseSuccessfullyDto resp = branchService.getAllBranchListResponse();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getBranchById(Integer id) {
        log.info("GET /branch/{}", id);
        ResponseSuccessfullyDto resp = branchService.getBranch(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }
}
