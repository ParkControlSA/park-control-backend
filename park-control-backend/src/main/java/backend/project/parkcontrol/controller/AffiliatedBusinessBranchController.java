package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.AffiliatedBusinessBranchApi;
import backend.project.parkcontrol.dto.request.NewAffiliatedBusinessBranchDto;
import backend.project.parkcontrol.dto.response.AffiliatedBusinessBranchDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.AffiliatedBusinessBranchService;
import backend.project.parkcontrol.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AffiliatedBusinessBranchController implements AffiliatedBusinessBranchApi {

    private final AffiliatedBusinessBranchService affiliatedBusinessBranchService;

    private final TokenService tokenService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createAffiliatedBusinessBranch(NewAffiliatedBusinessBranchDto dto) {
        log.info("POST /affiliatedBusinessBranch");
        ResponseSuccessfullyDto resp = affiliatedBusinessBranchService.createAffiliatedBusinessBranch(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateAffiliatedBusinessBranch(AffiliatedBusinessBranchDto dto) {
        log.info("PUT /affiliatedBusinessBranch");
        ResponseSuccessfullyDto resp = affiliatedBusinessBranchService.updateAffiliatedBusinessBranch(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteAffiliatedBusinessBranch(Integer id) {
        log.info("DELETE /affiliatedBusinessBranch/{}", id);
        ResponseSuccessfullyDto resp = affiliatedBusinessBranchService.deleteAffiliatedBusinessBranch(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }


    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllAffiliatedBusinessBranchs() {
        log.info("GET /affiliatedBusinessBranch/all");
        ResponseSuccessfullyDto resp = affiliatedBusinessBranchService.getAllAffiliatedBusinessBranchListResponse();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAffiliatedBusinessBranchById(Integer id) {
        log.info("GET /affiliatedBusinessBranch/{}", id);
        ResponseSuccessfullyDto resp = affiliatedBusinessBranchService.getAffiliatedBusinessBranch(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getByIdBranch(Integer id) {
        log.info("GET /affiliatedBusinessBranch/branch/{}", id);
        ResponseSuccessfullyDto resp = affiliatedBusinessBranchService.getById_branchResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getByIdAffiliatedBusiness(Integer id) {
        log.info("GET /affiliatedBusinessBranch/affiliatedBusiness/{}", id);
        ResponseSuccessfullyDto resp = affiliatedBusinessBranchService.getById_affiliated_businessResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }
}
