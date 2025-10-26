package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewBranchManagerDto;
import backend.project.parkcontrol.dto.response.BranchManagerDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.BranchManagerCrud;
import backend.project.parkcontrol.repository.entities.Branch;
import backend.project.parkcontrol.repository.entities.BranchManager;
import backend.project.parkcontrol.repository.entities.UserEntity;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BranchManagerServiceTest {

    @Mock
    private BranchManagerCrud branchManagerCrud;

    @Mock
    private BranchService branchService;

    @Mock
    private UserService userService;

    @InjectMocks
    private BranchManagerService service;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void getByIdUser() {
        Integer userId = 1;
        List<BranchManager> expected = Arrays.asList(
                GENERATOR.nextObject(BranchManager.class),
                GENERATOR.nextObject(BranchManager.class)
        );

        when(branchManagerCrud.findById_user(userId)).thenReturn(expected);

        List<BranchManager> result = service.getByIdUser(userId);

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(branchManagerCrud).findById_user(userId);
    }

    @Test
    void getByIdBranch() {
        Integer branchId = 1;
        List<BranchManager> expected = Arrays.asList(
                GENERATOR.nextObject(BranchManager.class)
        );

        when(branchManagerCrud.findById_branch(branchId)).thenReturn(expected);

        List<BranchManager> result = service.getByIdBranch(branchId);

        assertThat(result).isEqualTo(expected);
        verify(branchManagerCrud).findById_branch(branchId);
    }

    @Test
    void getAllBranchManagerList() {
        List<BranchManager> expected = Arrays.asList(
                GENERATOR.nextObject(BranchManager.class),
                GENERATOR.nextObject(BranchManager.class)
        );

        when(branchManagerCrud.findAll()).thenReturn(expected);

        List<BranchManager> result = service.getAllBranchManagerList();

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(branchManagerCrud).findAll();
    }

    @Test
    void getBranchManagerById() {
        Integer id = 5;
        BranchManager entity = GENERATOR.nextObject(BranchManager.class);
        entity.setId(id);

        when(branchManagerCrud.findById(id)).thenReturn(Optional.of(entity));

        BranchManager result = service.getBranchManagerById(id);

        assertThat(result).isEqualTo(entity);
        verify(branchManagerCrud).findById(id);
    }

    @Test
    void deleteBranchManager() {
        Integer id = 7;
        BranchManager entity = GENERATOR.nextObject(BranchManager.class);
        entity.setId(id);

        when(branchManagerCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.deleteBranchManager(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro eliminado con éxito");
        verify(branchManagerCrud).delete(entity);
    }

    @Test
    void createBranchManager_success() {
        NewBranchManagerDto dto = new NewBranchManagerDto();
        dto.setId_user(10);
        dto.setId_branch(20);

        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        Branch branch = GENERATOR.nextObject(Branch.class);
        BranchManager saved = new BranchManager();
        saved.setId(99);

        // No existing assignments
        when(branchManagerCrud.findById_branchId_user(dto.getId_branch(), dto.getId_user()))
                .thenReturn(Collections.emptyList());
        when(branchManagerCrud.findById_user(dto.getId_user()))
                .thenReturn(Collections.emptyList());

        when(userService.getUserById(dto.getId_user())).thenReturn(user);
        when(branchService.getBranchById(dto.getId_branch())).thenReturn(branch);
        when(branchManagerCrud.save(any(BranchManager.class))).thenReturn(saved);

        ResponseSuccessfullyDto resp = service.createBranchManager(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registro creado con éxito");
        verify(branchManagerCrud).save(any(BranchManager.class));
    }

    @Test
    void createBranchManager_userAlreadyAssignedToBranch() {
        NewBranchManagerDto dto = new NewBranchManagerDto();
        dto.setId_user(11);
        dto.setId_branch(22);

        // simulate existing assignment to same branch
        when(branchManagerCrud.findById_branchId_user(dto.getId_branch(), dto.getId_user()))
                .thenReturn(Collections.singletonList(GENERATOR.nextObject(BranchManager.class)));

        BusinessException ex = assertThrows(BusinessException.class, () ->
                service.createBranchManager(dto));

        assertThat(ex.getCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ex.getMessage()).contains("ya está asignado como encargado");
    }

    @Test
    void createBranchManager_userAlreadyAssignedToOtherBranch() {
        NewBranchManagerDto dto = new NewBranchManagerDto();
        dto.setId_user(11);
        dto.setId_branch(22);

        // No assignment to this branch but assigned to another
        when(branchManagerCrud.findById_branchId_user(dto.getId_branch(), dto.getId_user()))
                .thenReturn(Collections.emptyList());
        when(branchManagerCrud.findById_user(dto.getId_user()))
                .thenReturn(Collections.singletonList(GENERATOR.nextObject(BranchManager.class)));

        BusinessException ex = assertThrows(BusinessException.class, () ->
                service.createBranchManager(dto));

        assertThat(ex.getCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ex.getMessage()).contains("ya está asignado a una sucursal distinta");
    }

    @Test
    void updateBranchManager() {
        BranchManagerDto dto = new BranchManagerDto();
        dto.setId(55);
        dto.setId_user(101);
        dto.setId_branch(202);

        BranchManager existing = GENERATOR.nextObject(BranchManager.class);
        existing.setId(dto.getId());
        UserEntity user = GENERATOR.nextObject(UserEntity.class);
        Branch branch = GENERATOR.nextObject(Branch.class);

        when(branchManagerCrud.findById(dto.getId())).thenReturn(Optional.of(existing));
        when(userService.getUserById(dto.getId_user())).thenReturn(user);
        when(branchService.getBranchById(dto.getId_branch())).thenReturn(branch);
        when(branchManagerCrud.save(any(BranchManager.class))).thenReturn(existing);

        ResponseSuccessfullyDto resp = service.updateBranchManager(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro actualizado con éxito");
        verify(branchManagerCrud).save(any(BranchManager.class));
    }

    @Test
    void getBranchManager_responses() {
        Integer id = 33;
        BranchManager entity = GENERATOR.nextObject(BranchManager.class);
        entity.setId(id);

        when(branchManagerCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto single = service.getBranchManager(id);
        assertThat(single.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(single.getMessage()).isEqualTo("Registro encontrado con éxito");
        assertThat(single.getBody()).isEqualTo(entity);

        List<BranchManager> list = Arrays.asList(entity);
        when(branchManagerCrud.findAll()).thenReturn(list);
        ResponseSuccessfullyDto all = service.getAllBranchManagerListResponse();
        assertThat(all.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(all.getMessage()).isEqualTo("Registros encontrados con éxito");
        assertThat(all.getBody()).isEqualTo(list);

        when(branchManagerCrud.findById_branch(id)).thenReturn(list);
        ResponseSuccessfullyDto byBranch = service.getByIdBranchResponse(id);
        assertThat(byBranch.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(byBranch.getMessage()).isEqualTo("Registros encontrados con éxito");
        assertThat(byBranch.getBody()).isEqualTo(list);

        when(branchManagerCrud.findById_user(id)).thenReturn(list);
        ResponseSuccessfullyDto byUser = service.getByIdUserResponse(id);
        assertThat(byUser.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(byUser.getMessage()).isEqualTo("Registros encontrados con éxito");
        assertThat(byUser.getBody()).isEqualTo(list);
    }
}