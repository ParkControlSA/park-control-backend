package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewBranchTemporaryPermitDto;
import backend.project.parkcontrol.dto.response.BranchTemporaryPermitDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.BranchTemporaryPermitCrud;
import backend.project.parkcontrol.repository.entities.BranchTemporaryPermit;
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

    // ==============================
    // GETTERS
    // ==============================

    public List<BranchTemporaryPermit> getById_temporary_permit(Integer id) {
        List<BranchTemporaryPermit> list = branchtemporarypermitCrud.findById_temporary_permit(id);
        if (list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    public List<BranchTemporaryPermit> getById_branch(Integer id) {
        List<BranchTemporaryPermit> list = branchtemporarypermitCrud.findById_branch(id);
        if (list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "Not found");
        return list;
    }

    public List<BranchTemporaryPermit> getAllBranchTemporaryPermitList() {
        List<BranchTemporaryPermit> list = branchtemporarypermitCrud.findAll();
        if (list.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "No records");
        return list;
    }

    public BranchTemporaryPermit getBranchTemporaryPermitById(Integer id) {
        Optional<BranchTemporaryPermit> optional = branchtemporarypermitCrud.findById(id);
        if (optional.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, "BranchTemporaryPermit not found");
        return optional.get();
    }

    // ==============================
    // CRUD Methods with ResponseSuccessfullyDto
    // ==============================

    public ResponseSuccessfullyDto deleteBranchTemporaryPermit(Integer id) {
        BranchTemporaryPermit entity = getBranchTemporaryPermitById(id);
        branchtemporarypermitCrud.delete(entity);
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Registro eliminado con Éxito")
                .build();
    }

    public ResponseSuccessfullyDto createBranchTemporaryPermit(NewBranchTemporaryPermitDto dto) {
        BranchTemporaryPermit e = new BranchTemporaryPermit();
        e.setTemporaryContractPermit(temporaryContractPermitService.getTemporaryContractPermitById(dto.getId_temporary_permit()));
        e.setBranch(branchService.getBranchById(dto.getId_branch()));
        branchtemporarypermitCrud.save(e);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Registro creado con Éxito")
                .build();
    }

    public ResponseSuccessfullyDto updateBranchTemporaryPermit(BranchTemporaryPermitDto dto) {
        BranchTemporaryPermit existing = getBranchTemporaryPermitById(dto.getId());
        existing.setTemporaryContractPermit(temporaryContractPermitService.getTemporaryContractPermitById(dto.getId_temporary_permit()));
        existing.setBranch(branchService.getBranchById(dto.getId_branch()));
        branchtemporarypermitCrud.save(existing);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Registro actualizado con Éxito")
                .build();
    }

    // ==============================
    // ResponseSuccessfullyDto Getters
    // ==============================

    public ResponseSuccessfullyDto getBranchTemporaryPermit(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registro encontrado con Éxito")
                .body(getBranchTemporaryPermitById(id))
                .build();
    }

    public ResponseSuccessfullyDto getAllBranchTemporaryPermitListResponse() {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getAllBranchTemporaryPermitList())
                .build();
    }

    public ResponseSuccessfullyDto getById_branchResponse(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getById_branch(id))
                .build();
    }

    public ResponseSuccessfullyDto getById_temporary_permitResponse(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getById_temporary_permit(id))
                .build();
    }
}
