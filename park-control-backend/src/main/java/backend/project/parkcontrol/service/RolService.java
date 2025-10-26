package backend.project.parkcontrol.service;


import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.RoleInfoDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.RolCrud;
import backend.project.parkcontrol.repository.entities.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RolService {

    private final RolCrud rolCrud;

    public RoleEntity getRoleById(Integer id){

        Optional<RoleEntity> optionalRole = rolCrud.findById(id);

        if(optionalRole.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"Role not exists");
        }

        return optionalRole.get();
    }

    public ResponseSuccessfullyDto getAllRoles(){

        List<RoleEntity> roles = rolCrud.findAll();
        List<RoleInfoDto> roleInfoDtoList = new ArrayList<>();

        roles.forEach(role -> {
            RoleInfoDto roleInfoDto = RoleInfoDto.builder().roleId(role.getId()).roleName(role.getRol()).build();
            roleInfoDtoList.add(roleInfoDto);
        });

        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).body(roleInfoDtoList).build();

    }


}
