package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.NewContractDto;
import backend.project.parkcontrol.dto.response.ContractDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/contract")
public interface ContractApi {

    // CRUD
    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createContract(@RequestBody NewContractDto dto);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateContract(@RequestBody ContractDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteContract(@PathVariable Integer id);

    // GETTERS
    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllContracts();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getContractById(@PathVariable Integer id);

    @GetMapping("/user/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getContractsByUserId(@PathVariable Integer id);

    @GetMapping("/plate/{plate}")
    ResponseEntity<ResponseSuccessfullyDto> getContractsByPlate(@PathVariable String plate);
}
