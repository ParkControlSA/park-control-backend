package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewTemporaryContractPermitDto;
import backend.project.parkcontrol.dto.response.TemporaryContractPermitDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.TemporaryContractPermitCrud;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.TemporaryContractPermit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TemporaryContractPermitService {
    private final TemporaryContractPermitCrud temporarycontractpermitCrud;
    private final ContractService contractService;
    private final UserCrud userCrud;

    // FK helper: find by id_contract
    public List<TemporaryContractPermit> getById_contract(Integer id){
        List<TemporaryContractPermit> list = temporarycontractpermitCrud.findById_contract(id);
        if(list.isEmpty()) throw new backend.project.parkcontrol.exception.BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    // FK helper: find by id_assigned
    public List<TemporaryContractPermit> getById_assigned(Integer id){
        List<TemporaryContractPermit> list = temporarycontractpermitCrud.findById_assigned(id);
        if(list.isEmpty()) throw new backend.project.parkcontrol.exception.BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    public List<TemporaryContractPermit> getAllTemporaryContractPermitList(){
        List<TemporaryContractPermit> list = temporarycontractpermitCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No records");
        return list;
    }

    public TemporaryContractPermit getTemporaryContractPermitById(Integer id){
        Optional<TemporaryContractPermit> optional = temporarycontractpermitCrud.findById(id);
        if(optional.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "TemporaryContractPermit not found");
        return optional.get();
    }

    public void deleteTemporaryContractPermit(Integer id){
        TemporaryContractPermit entity = getTemporaryContractPermitById(id);
        temporarycontractpermitCrud.delete(entity);
    }

    public void createTemporaryContractPermit(NewTemporaryContractPermitDto dto){
        TemporaryContractPermit e = new TemporaryContractPermit();
        e.setContract(contractService.getContractById(dto.getId_contract()));
        e.setTemporary_plate(dto.getTemporary_plate());
        e.setStart_date(dto.getStart_date());
        e.setEnd_date(dto.getEnd_date());
        e.setMax_uses(dto.getMax_uses());
        e.setUsed_count(dto.getUsed_count());
        e.setRemaining_count(dto.getRemaining_count());
        e.setIs_4r(dto.getIs_4r());
        e.setStatus(dto.getStatus());
        e.setObservations(dto.getObservations());
        e.setEncargado(userCrud.findById(dto.getId_assigned()).get());

        temporarycontractpermitCrud.save(e);
    }

    public void updateTemporaryContractPermit(TemporaryContractPermitDto dto){
        TemporaryContractPermit existing = getTemporaryContractPermitById(dto.getId());
        existing.setContract(contractService.getContractById(dto.getId_contract()));
        existing.setTemporary_plate(dto.getTemporary_plate());
        existing.setStart_date(dto.getStart_date());
        existing.setEnd_date(dto.getEnd_date());
        existing.setMax_uses(dto.getMax_uses());
        existing.setUsed_count(dto.getUsed_count());
        existing.setRemaining_count(dto.getRemaining_count());
        existing.setIs_4r(dto.getIs_4r());
        existing.setStatus(dto.getStatus());
        existing.setObservations(dto.getObservations());
        existing.setEncargado(userCrud.findById(dto.getId_assigned()).get());

        temporarycontractpermitCrud.save(existing);
    }
}
