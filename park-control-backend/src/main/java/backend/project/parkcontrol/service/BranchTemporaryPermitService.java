package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewBranchTemporaryPermitDto;
import backend.project.parkcontrol.dto.response.BranchTemporaryPermitDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.BranchTemporaryPermitCrud;
import backend.project.parkcontrol.repository.entities.BranchTemporaryPermit;
import backend.project.parkcontrol.repository.entities.TemporaryContractPermit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class BranchTemporaryPermitService {
    private final BranchTemporaryPermitCrud branchtemporarypermitCrud;
    private final TemporaryContractPermitService temporaryContractPermitService;
    private final BranchService branchService;

    // FK helper: find by id_temporary_permit
    public List<BranchTemporaryPermit> getById_temporary_permit(Integer id){
        List<BranchTemporaryPermit> list = branchtemporarypermitCrud.findById_temporary_permit(id);
        if(list.isEmpty()) throw new backend.project.parkcontrol.exception.BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    // FK helper: find by id_branch
    public List<BranchTemporaryPermit> getById_branch(Integer id){
        List<BranchTemporaryPermit> list = branchtemporarypermitCrud.findById_branch(id);
        if(list.isEmpty()) throw new backend.project.parkcontrol.exception.BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    public List<BranchTemporaryPermit> getAllBranchTemporaryPermitList(){
        List<BranchTemporaryPermit> list = branchtemporarypermitCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No records");
        return list;
    }

    public BranchTemporaryPermit getBranchTemporaryPermitById(Integer id){
        Optional<BranchTemporaryPermit> optional = branchtemporarypermitCrud.findById(id);
        if(optional.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "BranchTemporaryPermit not found");
        return optional.get();
    }

    public void deleteBranchTemporaryPermit(Integer id){
        BranchTemporaryPermit entity = getBranchTemporaryPermitById(id);
        branchtemporarypermitCrud.delete(entity);
    }

    public void createBranchTemporaryPermit(NewBranchTemporaryPermitDto dto){
        BranchTemporaryPermit e = new BranchTemporaryPermit();
        e.setTemporaryContractPermit(temporaryContractPermitService.getTemporaryContractPermitById(dto.getId_temporary_permit()));
        e.setBranch(branchService.getBranchById(dto.getId_branch()));
        branchtemporarypermitCrud.save(e);
    }

    public void updateBranchTemporaryPermit(BranchTemporaryPermitDto dto){
        BranchTemporaryPermit existing = getBranchTemporaryPermitById(dto.getId());
        existing.setTemporaryContractPermit(temporaryContractPermitService.getTemporaryContractPermitById(dto.getId_temporary_permit()));
        existing.setBranch(branchService.getBranchById(dto.getId_branch()));

        branchtemporarypermitCrud.save(existing);
    }
}
