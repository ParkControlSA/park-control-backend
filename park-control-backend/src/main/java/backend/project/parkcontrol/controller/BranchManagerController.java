package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.BranchManagerApi;
import backend.project.parkcontrol.dto.request.NewBranchManagerDto;
import backend.project.parkcontrol.dto.response.BranchManagerDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.BranchManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BranchManagerController implements BranchManagerApi {

    private final BranchManagerService branchManagerService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createBranchManager(NewBranchManagerDto dto) {
        log.info("POST /branchManager");
        ResponseSuccessfullyDto resp = branchManagerService.createBranchManager(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateBranchManager(BranchManagerDto dto) {
        log.info("PUT /branchManager");
        ResponseSuccessfullyDto resp = branchManagerService.updateBranchManager(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteBranchManager(Integer id) {
        log.info("DELETE /branchManager/{}", id);
        ResponseSuccessfullyDto resp = branchManagerService.deleteBranchManager(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllBranchManagers() {
        log.info("GET /branchManager/all");
        ResponseSuccessfullyDto resp = branchManagerService.getAllBranchManagerListResponse();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getBranchManagerById(Integer id) {
        log.info("GET /branchManager/{}", id);
        ResponseSuccessfullyDto resp = branchManagerService.getBranchManager(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getByIdBranch(Integer id) {
        log.info("GET /branchManager/branch/{}", id);
        ResponseSuccessfullyDto resp = branchManagerService.getByIdBranchResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getByIdUser(Integer id) {
        log.info("GET /branchManager/user/{}", id);
        ResponseSuccessfullyDto resp = branchManagerService.getByIdUserResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }
}
