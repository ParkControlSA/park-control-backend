package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.NewBranchDto;
import backend.project.parkcontrol.dto.response.BranchDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/branch")
public interface BranchApi {

    // CRUD
    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createBranch(@RequestBody NewBranchDto dto);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateBranch(@RequestBody BranchDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteBranch(@PathVariable Integer id);

    // GETTERS
    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllbranch();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getBranchById(@PathVariable Integer id);
}
