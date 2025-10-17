package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewRateAssignmentDto;
import backend.project.parkcontrol.dto.response.RateAssignmentDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.RateAssignmentCrud;
import backend.project.parkcontrol.repository.entities.RateAssignment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class RateAssignmentService {
    private final RateAssignmentCrud rateassignmentCrud;
    private final BranchService branchService;
    // FK helper: find by id_branch
    public java.util.List<RateAssignment> getById_branch(Integer id){
        java.util.List<RateAssignment> list = rateassignmentCrud.findById_branch(id);
        if(list.isEmpty()) throw new BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    public java.util.List<RateAssignment> getAllRateAssignmentList(){
        java.util.List<RateAssignment> list = rateassignmentCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No records");
        return list;
    }

    public RateAssignment getRateAssignmentById(Integer id){
        Optional<RateAssignment> optional = rateassignmentCrud.findById(id);
        if(optional.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "RateAssignment not found");
        return optional.get();
    }

    public void deleteRateAssignment(Integer id){
        RateAssignment entity = getRateAssignmentById(id);
        rateassignmentCrud.delete(entity);
    }

    public void createRateAssignment(NewRateAssignmentDto dto){
        RateAssignment e = new RateAssignment();
        e.setBranch(branchService.getBranchById(dto.getId_branch()));
        e.setHourly_rate(dto.getHourly_rate());
        e.setIs_active(dto.getIs_active());
        e.setInsert_date(dto.getInsert_date());
        e.setUpdate_date(dto.getUpdate_date());

        rateassignmentCrud.save(e);
    }

    public void updateRateAssignment(RateAssignmentDto dto){
        RateAssignment existing = getRateAssignmentById(dto.getId());
        existing.setBranch(branchService.getBranchById(dto.getId_branch()));
        existing.setHourly_rate(dto.getHourly_rate());
        existing.setIs_active(dto.getIs_active());
        existing.setInsert_date(dto.getInsert_date());
        existing.setUpdate_date(dto.getUpdate_date());

        rateassignmentCrud.save(existing);
    }
}
