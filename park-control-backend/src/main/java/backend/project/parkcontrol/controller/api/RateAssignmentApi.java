package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.NewRateAssignmentDto;
import backend.project.parkcontrol.dto.response.RateAssignmentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/rateAssignment")
public interface RateAssignmentApi {

    // CRUD
    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> create(@RequestBody NewRateAssignmentDto dto);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> update(@RequestBody RateAssignmentDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> delete(@PathVariable Integer id);

    // GETTERS
    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAll();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getById(@PathVariable Integer id);

    @GetMapping("/branch/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getByBranchId(@PathVariable Integer id);

    @GetMapping("/branch/active/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getRateAssignamentById_branchIsActive(@PathVariable Integer id);
}
