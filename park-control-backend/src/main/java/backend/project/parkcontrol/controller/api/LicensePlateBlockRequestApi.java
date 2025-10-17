package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.NewLicensePlateBlockRequestDto;
import backend.project.parkcontrol.dto.response.LicensePlateBlockRequestDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/licensePlateBlockRequest")
public interface LicensePlateBlockRequestApi {

    // CRUD
    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createLicensePlateBlockRequest(@RequestBody NewLicensePlateBlockRequestDto dto);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateLicensePlateBlockRequest(@RequestBody LicensePlateBlockRequestDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteLicensePlateBlockRequest(@PathVariable Integer id);

    // GETTERS
    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllLicensePlateBlockRequest();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getById(@PathVariable Integer id);

    @GetMapping("/contract/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getByContractId(@PathVariable Integer id);

    @GetMapping("/assigned/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getByAssignedUserId(@PathVariable Integer id);
}
