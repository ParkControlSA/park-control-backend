package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.AffiliateCommerceApi;
import backend.project.parkcontrol.dto.request.CreateAffiliateCommerceDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.repository.crud.AffiliateCommerceCrud;
import backend.project.parkcontrol.repository.entities.AffiliateCommerceEntity;
import backend.project.parkcontrol.service.AffiliateCommerceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AffiliateCommerceController  implements AffiliateCommerceApi {

    private final AffiliateCommerceService affiliateCommerceService;


    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createAffiliateCommerce(CreateAffiliateCommerceDto createAffiliateCommerceDto) {
        log.info("POST /affiliate_commerce");
        ResponseSuccessfullyDto responseSuccessfullyDto = affiliateCommerceService.createAffiliateCommerce(createAffiliateCommerceDto);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }
}
