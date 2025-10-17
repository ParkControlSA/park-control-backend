package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewAffiliatedBusinessBranchDto;
import backend.project.parkcontrol.dto.response.AffiliatedBusinessBranchDto;
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
    // FK helper: find by id_affiliated_business
    public List<AffiliatedBusinessBranch> getById_affiliated_business(Integer id){
        List<AffiliatedBusinessBranch> list = affiliatedbusinessbranchCrud.findById_affiliated_business(id);
        if(list.isEmpty()) throw new backend.project.parkcontrol.exception.BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    // FK helper: find by id_branch
    public List<AffiliatedBusinessBranch> getById_branch(Integer id){
        List<AffiliatedBusinessBranch> list = affiliatedbusinessbranchCrud.findById_branch(id);
        if(list.isEmpty()) throw new backend.project.parkcontrol.exception.BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    public List<AffiliatedBusinessBranch> getAllAffiliatedBusinessBranchList(){
        List<AffiliatedBusinessBranch> list = affiliatedbusinessbranchCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No records");
        return list;
    }

    public AffiliatedBusinessBranch getAffiliatedBusinessBranchById(Integer id){
        Optional<AffiliatedBusinessBranch> optional = affiliatedbusinessbranchCrud.findById(id);
        if(optional.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "AffiliatedBusinessBranch not found");
        return optional.get();
    }

    public void deleteAffiliatedBusinessBranch(Integer id){
        AffiliatedBusinessBranch entity = getAffiliatedBusinessBranchById(id);
        affiliatedbusinessbranchCrud.delete(entity);
    }

    public void createAffiliatedBusinessBranch(NewAffiliatedBusinessBranchDto dto){
        AffiliatedBusinessBranch e = new AffiliatedBusinessBranch();
        e.setAffiliatedBusiness(affiliatedBusinessService.getAffiliatedBusinessById(dto.getId_affiliated_business()));
        e.setBranch(branchService.getBranchById(dto.getId_branch()));

        affiliatedbusinessbranchCrud.save(e);
    }

    public void updateAffiliatedBusinessBranch(AffiliatedBusinessBranchDto dto){
        AffiliatedBusinessBranch existing = getAffiliatedBusinessBranchById(dto.getId());
        existing.setAffiliatedBusiness(affiliatedBusinessService.getAffiliatedBusinessById(dto.getId_affiliated_business()));
        existing.setBranch(branchService.getBranchById(dto.getId_branch()));

        affiliatedbusinessbranchCrud.save(existing);
    }
}
