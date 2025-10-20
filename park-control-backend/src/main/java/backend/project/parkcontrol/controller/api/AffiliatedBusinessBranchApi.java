package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.NewAffiliatedBusinessBranchDto;
import backend.project.parkcontrol.dto.response.AffiliatedBusinessBranchDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/affiliatedBusinessBranch")
public interface AffiliatedBusinessBranchApi {


    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createAffiliatedBusinessBranch(@RequestBody NewAffiliatedBusinessBranchDto dto,
                                                                           @RequestHeader(name = "authorization") Integer userId);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateAffiliatedBusinessBranch(@RequestBody AffiliatedBusinessBranchDto dto,
                                                                           @RequestHeader(name = "authorization") Integer userId);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteAffiliatedBusinessBranch(@PathVariable Integer id,
                                                                           @RequestHeader(name = "authorization") Integer userId);


    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllAffiliatedBusinessBranchs();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getAffiliatedBusinessBranchById(@PathVariable Integer id);

    @GetMapping("/branch/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getByIdBranch(@PathVariable Integer id);

    @GetMapping("/affiliatedBusiness/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getByIdAffiliatedBusiness(@PathVariable Integer id);
}
