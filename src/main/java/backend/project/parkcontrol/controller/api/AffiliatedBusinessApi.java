package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.NewAffiliatedBusinessDto;
import backend.project.parkcontrol.dto.response.AffiliatedBusinessDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/affiliatedBusinesss")
public interface AffiliatedBusinessApi {
    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createAffiliatedBusiness(@RequestBody NewAffiliatedBusinessDto dto);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateAffiliatedBusiness(@RequestBody AffiliatedBusinessDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteAffiliatedBusiness(@PathVariable Integer id);

    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllAffiliatedBusinesss();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getAffiliatedBusinessById(@PathVariable Integer id);

    @GetMapping("/user/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getAffiliatedBusinessByIdUser(@PathVariable Integer id);
}
