package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.NewContractHistoryDto;
import backend.project.parkcontrol.dto.response.ContractHistoryDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/contractHistory")
public interface ContractHistoryApi {

    // CRUD
   /* @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createContractHistory(@RequestBody NewContractHistoryDto dto);
*/
    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateContractHistory(@RequestBody ContractHistoryDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteContractHistory(@PathVariable Integer id);

    // GETTERS
    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllContractHistorys();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getContractHistoryById(@PathVariable Integer id);

    @GetMapping("/contract/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getContractHistoryByContractId(@PathVariable Integer id);
}
