package backend.project.parkcontrol.service;


import backend.project.parkcontrol.dto.request.CreateAffiliateCommerceDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.mapper.AffiliateCommerceMapper;
import backend.project.parkcontrol.repository.crud.AffiliateCommerceCrud;
import backend.project.parkcontrol.repository.entities.AffiliateCommerceEntity;
import backend.project.parkcontrol.repository.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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


    public ResponseSuccessfullyDto deleteAffiliateCommerce(Integer id){
        AffiliateCommerceEntity entity = getAffiliateCommerceById(id);
        affiliateCommerceCrud.delete(entity);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("El comercio afiliado ha sido eliminado correctamente")
                .build();
    }

    // ==============================
    // GETTERS
    // ==============================
    public AffiliateCommerceEntity getAffiliateCommerceById(Integer id){
        Optional<AffiliateCommerceEntity> optional = affiliateCommerceCrud.findById(id);
        if(optional.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "Comercio afiliado no encontrado");
        return optional.get();
    }

    public ResponseSuccessfullyDto getAffiliateCommerceByIdResponse(Integer id){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registro encontrado con éxito")
                .body(getAffiliateCommerceById(id))
                .build();
    }

    public List<AffiliateCommerceEntity> getAllAffiliateCommerceList(){
        List<AffiliateCommerceEntity> list = affiliateCommerceCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No hay registros de comercios afiliados");
        return list;
    }

    public ResponseSuccessfullyDto getAllAffiliateCommerceListResponse(){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con éxito")
                .body(getAllAffiliateCommerceList())
                .build();
    }
/*
    // FK helper: find by user ID
    public List<AffiliateCommerceEntity> getByUserId(Integer idUsuario){
        List<AffiliateCommerceEntity> list = affiliateCommerceCrud.get(idUsuario);
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontraron comercios para el usuario");
        return list;
    }

    public ResponseSuccessfullyDto getByUserIdResponse(Integer idUsuario){
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con éxito")
                .body(getByUserId(idUsuario))
                .build();
    }
}
*/

}
