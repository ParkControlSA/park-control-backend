package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.NewBranchTemporaryPermitDto;
import backend.project.parkcontrol.dto.response.BranchTemporaryPermitDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/branchTemporaryPermit")
public interface BranchTemporaryPermitApi {

    // CRUD
    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createBranchTemporaryPermit(@RequestBody NewBranchTemporaryPermitDto dto);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateBranchTemporaryPermit(@RequestBody BranchTemporaryPermitDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteBranchTemporaryPermit(@PathVariable Integer id);

    // GETTERS
    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllbranchTemporaryPermit();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getBranchTemporaryPermitById(@PathVariable Integer id);

    @GetMapping("/branch/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getByIdBranch(@PathVariable Integer id);

    @GetMapping("/temporaryPermit/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getByIdTemporaryPermit(@PathVariable Integer id);
}
