package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.NewTemporaryContractPermitDto;
import backend.project.parkcontrol.dto.response.TemporaryContractPermitDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/temporaryContractPermit")
public interface TemporaryContractPermitApi {

    // CRUD
    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> create(@RequestBody NewTemporaryContractPermitDto dto);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> update(@RequestBody TemporaryContractPermitDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> delete(@PathVariable Integer id);

    // GETTERS
    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAll();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getById(@PathVariable Integer id);

    @GetMapping("/contract/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getByContract(@PathVariable Integer id);

    @GetMapping("/assigned/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getByAssigned(@PathVariable Integer id);
}
