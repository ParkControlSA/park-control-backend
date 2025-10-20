package backend.project.parkcontrol.service;

import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class TokenService {

    private static final Integer ADMIN_ID = 1;

    private final UserCrud userCrud;


    public void validateAuthorizationHeader(Integer userId){
        Optional<UserEntity> optionalUser = userCrud.findById(userId);

        if(optionalUser.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND, "Credenciales incorrectas");
        }

        UserEntity user = optionalUser.get();

        if(!user.getRol().getId().equals(ADMIN_ID)){
            throw new BusinessException(HttpStatus.UNAUTHORIZED,"El usuario no cuenta con permisos para realizar la acción");
        }
    }



}
