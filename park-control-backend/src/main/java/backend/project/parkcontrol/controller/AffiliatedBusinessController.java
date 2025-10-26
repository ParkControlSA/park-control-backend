package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.AffiliatedBusinessApi;
import backend.project.parkcontrol.dto.request.NewAffiliatedBusinessDto;
import backend.project.parkcontrol.dto.response.AffiliatedBusinessDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.AffiliatedBusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AffiliatedBusinessController implements AffiliatedBusinessApi {
    private final AffiliatedBusinessService affiliatedbusinessService;
    //CRUD
    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createAffiliatedBusiness(NewAffiliatedBusinessDto dto) {
        log.info("POST /affiliatedBusinesss");
        ResponseSuccessfullyDto resp = affiliatedbusinessService.createAffiliatedBusiness(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateAffiliatedBusiness(AffiliatedBusinessDto dto) {
        log.info("PUT /affiliatedBusinesss");
        ResponseSuccessfullyDto resp = affiliatedbusinessService.updateAffiliatedBusiness(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteAffiliatedBusiness(Integer id) {
        ResponseSuccessfullyDto resp = affiliatedbusinessService.deleteAffiliatedBusiness(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }
    //GETTERS
    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllAffiliatedBusinesss() {
        ResponseSuccessfullyDto resp = affiliatedbusinessService.getAllAffiliatedBusinessListResponse();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAffiliatedBusinessById(Integer id) {
        ResponseSuccessfullyDto resp = affiliatedbusinessService.getAffiliatedBusiness(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAffiliatedBusinessByIdUser(Integer id) {
        ResponseSuccessfullyDto resp = affiliatedbusinessService.getById_userResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

}
