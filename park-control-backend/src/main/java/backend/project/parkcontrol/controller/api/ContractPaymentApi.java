package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.NewContractPaymentDto;
import backend.project.parkcontrol.dto.response.ContractPaymentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/contractPayment")
public interface ContractPaymentApi {

    // CRUD
    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createContractPayment(@RequestBody NewContractPaymentDto dto);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateContractPayment(@RequestBody ContractPaymentDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteContractPayment(@PathVariable Integer id);

    // GETTERS
    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllContractPayments();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getContractPaymentById(@PathVariable Integer id);

    @GetMapping("/contract/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getContractPaymentsByContractId(@PathVariable Integer id);
}
