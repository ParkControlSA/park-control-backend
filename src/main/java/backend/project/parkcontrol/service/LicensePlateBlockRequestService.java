package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewLicensePlateBlockRequestDto;
import backend.project.parkcontrol.dto.response.LicensePlateBlockRequestDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.LicensePlateBlockRequestCrud;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.Contract;
import backend.project.parkcontrol.repository.entities.LicensePlateBlockRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class LicensePlateBlockRequestService {
    private final LicensePlateBlockRequestCrud licenseplateblockrequestCrud;
    private final ContractService contractService;
    private final UserCrud userCrud;
    // FK helper: find by id_contract
    public List<LicensePlateBlockRequest> getById_contract(Integer id){
        List<LicensePlateBlockRequest> list = licenseplateblockrequestCrud.findById_contract(id);
        if(list.isEmpty()) throw new BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    // FK helper: find by id_assigned
    public java.util.List<LicensePlateBlockRequest> getById_assigned(Integer id){
        java.util.List<LicensePlateBlockRequest> list = licenseplateblockrequestCrud.findById_assigned(id);
        if(list.isEmpty()) throw new BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    public java.util.List<LicensePlateBlockRequest> getAllLicensePlateBlockRequestList(){
        java.util.List<LicensePlateBlockRequest> list = licenseplateblockrequestCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No records");
        return list;
    }

    public LicensePlateBlockRequest getLicensePlateBlockRequestById(Integer id){
        Optional<LicensePlateBlockRequest> optional = licenseplateblockrequestCrud.findById(id);
        if(optional.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "LicensePlateBlockRequest not found");
        return optional.get();
    }

    public void deleteLicensePlateBlockRequest(Integer id){
        LicensePlateBlockRequest entity = getLicensePlateBlockRequestById(id);
        licenseplateblockrequestCrud.delete(entity);
    }

    public void createLicensePlateBlockRequest(NewLicensePlateBlockRequestDto dto){
        LicensePlateBlockRequest e = new LicensePlateBlockRequest();
        e.setIs_4r(dto.getIs_4r());
        e.setContract(contractService.getContractById(dto.getId_contract()));
        e.setUser(userCrud.findById(dto.getId_assigned()).get());
        e.setOld_plate(dto.getOld_plate());
        e.setNew_plate(dto.getNew_plate());
        e.setEvidence_url(dto.getEvidence_url());
        e.setNote(dto.getNote());
        e.setCreation_date(dto.getCreation_date());
        e.setStatus(dto.getStatus());

        licenseplateblockrequestCrud.save(e);
    }

    public void updateLicensePlateBlockRequest(LicensePlateBlockRequestDto dto){
        LicensePlateBlockRequest existing = getLicensePlateBlockRequestById(dto.getId());
        existing.setIs_4r(dto.getIs_4r());
        existing.setContract(contractService.getContractById(dto.getId_contract()));
        existing.setUser(userCrud.findById(dto.getId_assigned()).get());
        existing.setOld_plate(dto.getOld_plate());
        existing.setNew_plate(dto.getNew_plate());
        existing.setEvidence_url(dto.getEvidence_url());
        existing.setNote(dto.getNote());
        existing.setCreation_date(dto.getCreation_date());
        existing.setStatus(dto.getStatus());

        licenseplateblockrequestCrud.save(existing);
    }
}
