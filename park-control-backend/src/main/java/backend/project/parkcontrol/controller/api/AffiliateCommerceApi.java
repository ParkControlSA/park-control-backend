package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.CreateAffiliateCommerceDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.repository.entities.AffiliateCommerceEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/affiliate_commerce")
public interface AffiliateCommerceApi {


    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createAffiliateCommerce(CreateAffiliateCommerceDto createAffiliateCommerceDto);



}
