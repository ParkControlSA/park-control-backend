package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewBranchDto;
import backend.project.parkcontrol.dto.response.BranchDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.BranchCrud;
import backend.project.parkcontrol.repository.entities.Branch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class BranchService {
    private final BranchCrud branchCrud;

    public java.util.List<Branch> getAllBranchList(){
        List<Branch> list = branchCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No records");
        return list;
    }

    public Branch getBranchById(Integer id){
        Optional<Branch> optional = branchCrud.findById(id);
        if(optional.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "Branch not found");
        return optional.get();
    }

    public void deleteBranch(Integer id){
        Branch entity = getBranchById(id);
        branchCrud.delete(entity);
    }

    public void createBranch(NewBranchDto dto){
        Branch e = new Branch();
        e.setName(dto.getName());
        e.setAddress(dto.getAddress());
        e.setOpening_time(dto.getOpening_time());
        e.setClosing_time(dto.getClosing_time());
        e.setCapacity_2r(dto.getCapacity_2r());
        e.setCapacity_4r(dto.getCapacity_4r());

        branchCrud.save(e);
    }

    public void updateBranch(BranchDto dto){
        Branch existing = getBranchById(dto.getId());
        existing.setName(dto.getName());
        existing.setAddress(dto.getAddress());
        existing.setOpening_time(dto.getOpening_time());
        existing.setClosing_time(dto.getClosing_time());
        existing.setCapacity_2r(dto.getCapacity_2r());
        existing.setCapacity_4r(dto.getCapacity_4r());

        branchCrud.save(existing);
    }
}
