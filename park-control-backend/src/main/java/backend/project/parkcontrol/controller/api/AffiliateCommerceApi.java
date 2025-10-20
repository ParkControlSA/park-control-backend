package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.CreateAffiliateCommerceDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.repository.entities.AffiliateCommerceEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/affiliate_commerce")
public interface AffiliateCommerceApi {


    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createAffiliateCommerce(CreateAffiliateCommerceDto createAffiliateCommerceDto,
                                                                    @RequestHeader(name = "authorization") Integer userId);

    @PutMapping("/{affiliate_commerce_id}")
    ResponseEntity<ResponseSuccessfullyDto> updateAffiliateCommerce(CreateAffiliateCommerceDto createAffiliateCommerceDto,
                                                                    @PathVariable(name = "affiliate_commerce_id") Integer id,
                                                                    @RequestHeader(name = "authorization") Integer userId);


    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllAffiliateCommerce(@RequestHeader(name = "authorization") Integer token);

    



}
