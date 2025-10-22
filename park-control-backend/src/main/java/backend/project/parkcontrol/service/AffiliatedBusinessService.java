package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewAffiliatedBusinessDto;
import backend.project.parkcontrol.dto.response.AffiliatedBusinessDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
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
    private final ValidationService validationService;

    // ==============================
    // GETTERS
    // ==============================

    // FK helper: find by id_user
    public List<AffiliatedBusiness> getById_user(Integer id) {
        List<AffiliatedBusiness> list = affiliatedbusinessCrud.findById_user(id);
        return list;
    }

    public List<AffiliatedBusiness> getAllAffiliatedBusinessList() {
        List<AffiliatedBusiness> list = affiliatedbusinessCrud.findAll();
        return list;
    }

    public AffiliatedBusiness getAffiliatedBusinessById(Integer id) {
        Optional<AffiliatedBusiness> optional = affiliatedbusinessCrud.findById(id);
        return optional.get();
    }

    // ==============================
    // CRUD Methods with ResponseSuccessfullyDto
    // ==============================

    public ResponseSuccessfullyDto deleteAffiliatedBusiness(Integer id) {
        AffiliatedBusiness entity = getAffiliatedBusinessById(id);
        affiliatedbusinessCrud.delete(entity);
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Registro eliminado con Éxito")
                .build();
    }

    public ResponseSuccessfullyDto createAffiliatedBusiness(NewAffiliatedBusinessDto dto) {
        AffiliatedBusiness e = new AffiliatedBusiness();
        e.setBusiness_name(dto.getBusiness_name());
        validationService.validatePositiveNumber(dto.getGranted_hours(), "Horas Otorgadas");
        e.setGranted_hours(dto.getGranted_hours());
        e.setUser(userCrud.findById(dto.getId_user()).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "User not found")));

        affiliatedbusinessCrud.save(e);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Registro creado con Éxito")
                .build();
    }

    public ResponseSuccessfullyDto updateAffiliatedBusiness(AffiliatedBusinessDto dto) {
        AffiliatedBusiness existing = getAffiliatedBusinessById(dto.getId());
        existing.setBusiness_name(dto.getBusiness_name());
        validationService.validatePositiveNumber(dto.getGranted_hours(), "Horas Otorgadas");
        existing.setGranted_hours(dto.getGranted_hours());
        existing.setUser(userCrud.findById(dto.getId_user()).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "User not found")));

        affiliatedbusinessCrud.save(existing);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Registro actualizado con Éxito")
                .build();
    }

    // ==============================
    // ResponseSuccessfullyDto Getters
    // ==============================

    public ResponseSuccessfullyDto getAffiliatedBusiness(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registro encontrado con Éxito")
                .body(getAffiliatedBusinessById(id))
                .build();
    }

    public ResponseSuccessfullyDto getAllAffiliatedBusinessListResponse() {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getAllAffiliatedBusinessList())
                .build();
    }

    public ResponseSuccessfullyDto getById_userResponse(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getById_user(id))
                .build();
    }
}
