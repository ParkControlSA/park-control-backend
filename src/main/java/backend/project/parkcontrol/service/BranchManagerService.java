package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewBranchManagerDto;
import backend.project.parkcontrol.dto.response.BranchManagerDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.BranchManagerCrud;
import backend.project.parkcontrol.repository.entities.BranchManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class BranchManagerService {

    private final BranchManagerCrud branchManagerCrud;
    private final BranchService branchService;
    private final UserService userService;

    public List<BranchManager> getByIdUser(Integer id) {
        return branchManagerCrud.findById_user(id);
    }

    public List<BranchManager> getByIdBranch(Integer id) {
        return branchManagerCrud.findById_branch(id);
    }

    public List<BranchManager> getAllBranchManagerList() {
        return branchManagerCrud.findAll();
    }

    public BranchManager getBranchManagerById(Integer id) {
        return branchManagerCrud.findById(id).get();
    }

    public ResponseSuccessfullyDto deleteBranchManager(Integer id) {
        BranchManager entity = getBranchManagerById(id);
        branchManagerCrud.delete(entity);
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro eliminado con éxito")
                .build();
    }

    public ResponseSuccessfullyDto createBranchManager(NewBranchManagerDto dto) {
        validateBranchManager(dto.getId_user(), dto.getId_branch());

        BranchManager e = new BranchManager();
        e.setUser(userService.getUserById(dto.getId_user()));
        e.setBranch(branchService.getBranchById(dto.getId_branch()));
        branchManagerCrud.save(e);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.CREATED)
                .message("Registro creado con éxito")
                .build();
    }

    private void validateBranchManager(Integer idUser, Integer idBranch) {
        if (!branchManagerCrud.findById_branchId_user(idBranch, idUser).isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "El usuario ya está asignado como encargado de esta sucursal");
        }
        if (!branchManagerCrud.findById_user(idUser).isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "El usuario ya está asignado a una sucursal distinta");
        }
    }

    public ResponseSuccessfullyDto updateBranchManager(BranchManagerDto dto) {
        BranchManager existing = getBranchManagerById(dto.getId());
        existing.setUser(userService.getUserById(dto.getId_user()));
        existing.setBranch(branchService.getBranchById(dto.getId_branch()));
        branchManagerCrud.save(existing);

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Registro actualizado con éxito")
                .build();
    }

    public ResponseSuccessfullyDto getBranchManager(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registro encontrado con éxito")
                .body(getBranchManagerById(id))
                .build();
    }

    public ResponseSuccessfullyDto getAllBranchManagerListResponse() {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con éxito")
                .body(getAllBranchManagerList())
                .build();
    }

    public ResponseSuccessfullyDto getByIdBranchResponse(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con éxito")
                .body(getByIdBranch(id))
                .build();
    }

    public ResponseSuccessfullyDto getByIdUserResponse(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con éxito")
                .body(getByIdUser(id))
                .build();
    }
}
