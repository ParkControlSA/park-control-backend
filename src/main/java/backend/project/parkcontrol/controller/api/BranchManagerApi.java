package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.NewBranchManagerDto;
import backend.project.parkcontrol.dto.response.BranchManagerDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/branchManager")
public interface BranchManagerApi {

    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createBranchManager(@RequestBody NewBranchManagerDto dto);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateBranchManager(@RequestBody BranchManagerDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteBranchManager(@PathVariable Integer id);

    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllBranchManagers();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getBranchManagerById(@PathVariable Integer id);

    @GetMapping("/branch/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getByIdBranch(@PathVariable Integer id);

    @GetMapping("/user/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getByIdUser(@PathVariable Integer id);
}
