package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewAffiliatedBusinessDto;
import backend.project.parkcontrol.dto.response.AffiliatedBusinessDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.AffiliatedBusinessCrud;
import backend.project.parkcontrol.repository.crud.UserCrud;
import backend.project.parkcontrol.repository.entities.AffiliatedBusiness;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class AffiliatedBusinessService {
    private final AffiliatedBusinessCrud affiliatedbusinessCrud;
    private final UserCrud userCrud;

    // FK helper: find by id_user
    public List<AffiliatedBusiness> getById_user(Integer id){
        List<AffiliatedBusiness> list = affiliatedbusinessCrud.findById_user(id);
        if(list.isEmpty()) throw new backend.project.parkcontrol.exception.BusinessException(org.springframework.http.HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    public List<AffiliatedBusiness> getAllAffiliatedBusinessList(){
        List<AffiliatedBusiness> list = affiliatedbusinessCrud.findAll();
        if(list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No records");
        return list;
    }

    public AffiliatedBusiness getAffiliatedBusinessById(Integer id){
        Optional<AffiliatedBusiness> optional = affiliatedbusinessCrud.findById(id);
        if(optional.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "AffiliatedBusiness not found");
        return optional.get();
    }

    public void deleteAffiliatedBusiness(Integer id){
        AffiliatedBusiness entity = getAffiliatedBusinessById(id);
        affiliatedbusinessCrud.delete(entity);
    }

    public void createAffiliatedBusiness(NewAffiliatedBusinessDto dto){
        AffiliatedBusiness e = new AffiliatedBusiness();
        e.setBusiness_name(dto.getBusiness_name());
        e.setGranted_hours(dto.getGranted_hours());
        e.setUser(userCrud.findById(dto.getId_user()).get());

        affiliatedbusinessCrud.save(e);
    }

    public void updateAffiliatedBusiness(AffiliatedBusinessDto dto){
        AffiliatedBusiness existing = getAffiliatedBusinessById(dto.getId());
        existing.setBusiness_name(dto.getBusiness_name());
        existing.setGranted_hours(dto.getGranted_hours());
        existing.setUser(userCrud.findById(dto.getId_user()).get());

        affiliatedbusinessCrud.save(existing);
    }
}
