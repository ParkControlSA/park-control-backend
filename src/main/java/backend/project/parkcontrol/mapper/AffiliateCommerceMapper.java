package backend.project.parkcontrol.mapper;

import backend.project.parkcontrol.dto.request.CreateAffiliateCommerceDto;
import backend.project.parkcontrol.repository.entities.AffiliateCommerceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AffiliateCommerceMapper {

    public AffiliateCommerceEntity toAffiliateCommerceEntity(CreateAffiliateCommerceDto affiliateCommerceDto){

        AffiliateCommerceEntity affiliateCommerceEntity = new AffiliateCommerceEntity();
        affiliateCommerceEntity.setName(affiliateCommerceDto.getNombre());
        affiliateCommerceEntity.setHoursGranted(affiliateCommerceDto.getHorasOtorgadas());

        return affiliateCommerceEntity;
    }


}
