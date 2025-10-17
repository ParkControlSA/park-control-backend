package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewContractDto;
import backend.project.parkcontrol.dto.response.ContractDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.ContractCrud;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.Contract;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContractService {
    private final ContractCrud contractCrud;
    private final UserCrud userCrud;

    // FK helper: find by id_user
    public List<Contract> getById_user(Integer id){
        List<Contract> list = contractCrud.findById_user(id);
        if(list.isEmpty()) throw new BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    public java.util.List<Contract> getAllContractList(){
        java.util.List<Contract> list = contractCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No records");
        return list;
    }

    public Contract getContractById(Integer id){
        Optional<Contract> optional = contractCrud.findById(id);
        if(optional.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "Contract not found");
        return optional.get();
    }

    public void deleteContract(Integer id){
        Contract entity = getContractById(id);
        contractCrud.delete(entity);
    }

    public void createContract(NewContractDto dto){
        Contract e = new Contract();
        e.setUser(userCrud.findById(dto.getId_user()).get());
        e.setId_plan(dto.getId_plan());
        e.setIs_4r(dto.getIs_4r());
        e.setLicense_plate(dto.getLicense_plate());
        e.setStart_date(dto.getStart_date());
        e.setEnd_date(dto.getEnd_date());
        e.setMonths(dto.getMonths());
        e.setIs_anual(dto.getIs_anual());
        e.setActive(dto.getActive());

        contractCrud.save(e);
    }

    public void updateContract(ContractDto dto){
        Contract existing = getContractById(dto.getId());
        existing.setUser(userCrud.findById(dto.getId_user()).get());
        existing.setId_plan(dto.getId_plan());
        existing.setIs_4r(dto.getIs_4r());
        existing.setLicense_plate(dto.getLicense_plate());
        existing.setStart_date(dto.getStart_date());
        existing.setEnd_date(dto.getEnd_date());
        existing.setMonths(dto.getMonths());
        existing.setIs_anual(dto.getIs_anual());
        existing.setActive(dto.getActive());

        contractCrud.save(existing);
    }
}
