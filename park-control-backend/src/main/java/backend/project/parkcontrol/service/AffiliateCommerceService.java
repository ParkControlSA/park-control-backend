package backend.project.parkcontrol.service;


import backend.project.parkcontrol.dto.request.CreateAffiliateCommerceDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.mapper.AffiliateCommerceMapper;
import backend.project.parkcontrol.repository.crud.AffiliateCommerceCrud;
import backend.project.parkcontrol.repository.entities.AffiliateCommerceEntity;
import backend.project.parkcontrol.repository.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AffiliateCommerceService {

    private final UserService userService;

    private final AffiliateCommerceCrud affiliateCommerceCrud;

    private final AffiliateCommerceMapper affiliateCommerceMapper;

    public ResponseSuccessfullyDto createAffiliateCommerce(CreateAffiliateCommerceDto affiliateCommerceDto){
        UserEntity userEntity = userService.getUserById(affiliateCommerceDto.getIdUsuario());
        AffiliateCommerceEntity affiliateCommerceEntity = affiliateCommerceMapper.toAffiliateCommerceEntity(affiliateCommerceDto);
        affiliateCommerceEntity.setUser(userEntity);

        affiliateCommerceCrud.save(affiliateCommerceEntity);

        return ResponseSuccessfullyDto.builder().code(HttpStatus.CREATED).message("El comercio afiliado ha sido creado correctamente").build();
    }


    public ResponseSuccessfullyDto getAll(){
        return null;
    }


}
