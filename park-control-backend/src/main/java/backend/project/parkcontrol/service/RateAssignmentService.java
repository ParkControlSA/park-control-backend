package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewRateAssignmentDto;
import backend.project.parkcontrol.dto.response.RateAssignmentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.RateAssignmentCrud;
import backend.project.parkcontrol.repository.entities.RateAssignment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class RateAssignmentService {

    private final RateAssignmentCrud rateAssignmentCrud;
    private final BranchService branchService;
    private final ValidationService validationService;
    private static final Boolean ACTIVE_DEFAULT = true;
    private static final Boolean DESACTIVE_STATUS = false;
    // ==============================
    // GETTERS
    // ==============================

    public List<RateAssignment> getById_branch(Integer id) {
        List<RateAssignment> list = rateAssignmentCrud.findById_branch(id);
        return list;
    }

    public List<RateAssignment> getAllRateAssignmentList() {
        List<RateAssignment> list = rateAssignmentCrud.findAll();
        return list;
    }

    public List<RateAssignment> getRateAssignamentById_branchIsActive(Integer id) {
        List<RateAssignment> list = rateAssignmentCrud.findById_branchIsActive(id, true);
        return list;
    }

    public RateAssignment getRateAssignmentById(Integer id) {
        return rateAssignmentCrud.findById(id).get();
    }

    // ==============================
    // CRUD Methods
    // ==============================

    public ResponseSuccessfullyDto createRateAssignment(NewRateAssignmentDto dto) {
        RateAssignment e = new RateAssignment();
        e.setBranch(branchService.getBranchById(dto.getId_branch()));
        e.setHourly_rate(dto.getHourly_rate());
        e.setIs_active(ACTIVE_DEFAULT);
        e.setInsert_date(LocalDateTime.now());
        e.setUpdate_date(LocalDateTime.now());
        verifyRateAssignment(e.getBranch().getId());
        rateAssignmentCrud.save(e);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Registro creado con Éxito")
                .build();
    }

    private void verifyRateAssignment(Integer id) {
        if(!rateAssignmentCrud.findById_branchIsActive(id, true).isEmpty()){
            RateAssignment e = rateAssignmentCrud.findById_branchIsActive(id, true).getFirst();
            e.setIs_active(DESACTIVE_STATUS);
            rateAssignmentCrud.save(e);
        }
    }

    public ResponseSuccessfullyDto updateRateAssignment(RateAssignmentDto dto) {
        RateAssignment existing = getRateAssignmentById(dto.getId());
        existing.setBranch(branchService.getBranchById(dto.getId_branch()));
        existing.setHourly_rate(dto.getHourly_rate());
        existing.setIs_active(dto.getIs_active());
        validationService.validateCurrentOrFutureDateTime(dto.getInsert_date(),"Insert DateTime");
        validationService.validateCurrentOrFutureDateTime(dto.getInsert_date(),"Update DateTime");
        existing.setInsert_date(dto.getInsert_date());
        existing.setUpdate_date(dto.getUpdate_date());

        rateAssignmentCrud.save(existing);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro actualizado con Éxito")
                .build();
    }

    public ResponseSuccessfullyDto deleteRateAssignment(Integer id) {
        RateAssignment entity = getRateAssignmentById(id);
        rateAssignmentCrud.delete(entity);
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro eliminado con Éxito")
                .build();
    }

    // ==============================
    // ResponseSuccessfullyDto Getters
    // ==============================

    public ResponseSuccessfullyDto getRateAssignment(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registro encontrado con Éxito")
                .body(getRateAssignmentById(id))
                .build();
    }

    public ResponseSuccessfullyDto getAllRateAssignmentListResponse() {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getAllRateAssignmentList())
                .build();
    }

    public ResponseSuccessfullyDto getById_branchResponse(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getById_branch(id))
                .build();
    }

    public ResponseSuccessfullyDto getRateAssignamentById_branchIsActiveResponse(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getRateAssignamentById_branchIsActive(id))
                .build();
    }
}
