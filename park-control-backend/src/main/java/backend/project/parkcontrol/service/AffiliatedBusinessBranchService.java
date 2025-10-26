package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewAffiliatedBusinessBranchDto;
import backend.project.parkcontrol.dto.response.AffiliatedBusinessBranchDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.AffiliatedBusinessBranchCrud;
import backend.project.parkcontrol.repository.entities.AffiliatedBusinessBranch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class AffiliatedBusinessBranchService {
    private final AffiliatedBusinessBranchCrud affiliatedbusinessbranchCrud;
    private final AffiliatedBusinessService affiliatedBusinessService;
    private final BranchService branchService;

    public List<AffiliatedBusinessBranch> getById_affiliated_business(Integer id){
        List<AffiliatedBusinessBranch> list = affiliatedbusinessbranchCrud.findById_affiliated_business(id);
        return list;
    }

    public List<AffiliatedBusinessBranch> getById_branch(Integer id){
        List<AffiliatedBusinessBranch> list = affiliatedbusinessbranchCrud.findById_branch(id);
        return list;
    }

    public List<AffiliatedBusinessBranch> getAllAffiliatedBusinessBranchList(){
        List<AffiliatedBusinessBranch> list = affiliatedbusinessbranchCrud.findAll();
        return list;
    }

    public AffiliatedBusinessBranch getAffiliatedBusinessBranchById(Integer id){
        Optional<AffiliatedBusinessBranch> optional = affiliatedbusinessbranchCrud.findById(id);
        return optional.get();
    }

    public ResponseSuccessfullyDto deleteAffiliatedBusinessBranch(Integer id){
        AffiliatedBusinessBranch entity = getAffiliatedBusinessBranchById(id);
        affiliatedbusinessbranchCrud.delete(entity);
        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message("Registro eliminado con Exito").build();
    }

    public ResponseSuccessfullyDto createAffiliatedBusinessBranch(NewAffiliatedBusinessBranchDto dto){
        AffiliatedBusinessBranch e = new AffiliatedBusinessBranch();
        validateAffiliatedBusinessBranch(dto.getId_affiliated_business(),dto.getId_branch());
        e.setAffiliatedBusiness(affiliatedBusinessService.getAffiliatedBusinessById(dto.getId_affiliated_business()));
        e.setBranch(branchService.getBranchById(dto.getId_branch()));
        affiliatedbusinessbranchCrud.save(e);
        return ResponseSuccessfullyDto.builder().code(HttpStatus.CREATED).message("Registro creado con Exito").build();
    }

    private void validateAffiliatedBusinessBranch(Integer idAffiliatedBusiness, Integer idBranch) {
        if(!affiliatedbusinessbranchCrud.findById_branchId_affiliated_business(idBranch, idAffiliatedBusiness).isEmpty()){
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "El comercio yá está afiliado a la Sucursal");
        }
    }

    public ResponseSuccessfullyDto updateAffiliatedBusinessBranch(AffiliatedBusinessBranchDto dto){
        AffiliatedBusinessBranch existing = getAffiliatedBusinessBranchById(dto.getId());
        existing.setAffiliatedBusiness(affiliatedBusinessService.getAffiliatedBusinessById(dto.getId_affiliated_business()));
        existing.setBranch(branchService.getBranchById(dto.getId_branch()));
        affiliatedbusinessbranchCrud.save(existing);
        return ResponseSuccessfullyDto.builder().code(HttpStatus.OK).message("Registro actualizado con Exito").build();
    }

    public ResponseSuccessfullyDto getAffiliatedBusinessBranch(Integer id){
        return ResponseSuccessfullyDto.builder().code(HttpStatus.FOUND).message("Registro encontrado con Exito").body(getAffiliatedBusinessBranchById(id)).build();
    }

    public ResponseSuccessfullyDto getAllAffiliatedBusinessBranchListResponse(){
        return ResponseSuccessfullyDto.builder().code(HttpStatus.FOUND).message("Registro encontrado con Exito").body(getAllAffiliatedBusinessBranchList()).build();
    }

    public ResponseSuccessfullyDto getById_branchResponse(Integer id){
        return ResponseSuccessfullyDto.builder().code(HttpStatus.FOUND).message("Registro encontrado con Exito").body(getById_branch(id)).build();
    }

    public ResponseSuccessfullyDto getById_affiliated_businessResponse(Integer id){
        return ResponseSuccessfullyDto.builder().code(HttpStatus.FOUND).message("Registro encontrado con Exito").body(getById_affiliated_business(id)).build();
    }

}
