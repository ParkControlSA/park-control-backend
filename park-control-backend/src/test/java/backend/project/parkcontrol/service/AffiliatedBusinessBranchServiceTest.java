package backend.project.parkcontrol.service;


import backend.project.parkcontrol.dto.request.NewAffiliatedBusinessBranchDto;
import backend.project.parkcontrol.dto.response.AffiliatedBusinessBranchDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.AffiliatedBusinessBranchCrud;
import backend.project.parkcontrol.repository.entities.AffiliatedBusinessBranch;
import backend.project.parkcontrol.repository.entities.AffiliatedBusiness;
import backend.project.parkcontrol.repository.entities.Branch;
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
class AffiliatedBusinessBranchServiceTest {

    @Mock
    private AffiliatedBusinessBranchCrud affiliatedbusinessbranchCrud;

    @Mock
    private AffiliatedBusinessService affiliatedBusinessService;

    @Mock
    private BranchService branchService;

    @InjectMocks
    private AffiliatedBusinessBranchService service;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Test
    void getById_affiliated_business() {
        Integer id = 1;
        List<AffiliatedBusinessBranch> expected = Arrays.asList(
                GENERATOR.nextObject(AffiliatedBusinessBranch.class),
                GENERATOR.nextObject(AffiliatedBusinessBranch.class)
        );

        when(affiliatedbusinessbranchCrud.findById_affiliated_business(id)).thenReturn(expected);

        List<AffiliatedBusinessBranch> result = service.getById_affiliated_business(id);

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(affiliatedbusinessbranchCrud).findById_affiliated_business(id);
    }

    @Test
    void getById_branch() {
        Integer id = 2;
        List<AffiliatedBusinessBranch> expected = Arrays.asList(
                GENERATOR.nextObject(AffiliatedBusinessBranch.class)
        );

        when(affiliatedbusinessbranchCrud.findById_branch(id)).thenReturn(expected);

        List<AffiliatedBusinessBranch> result = service.getById_branch(id);

        assertThat(result).isEqualTo(expected);
        verify(affiliatedbusinessbranchCrud).findById_branch(id);
    }

    @Test
    void getAllAffiliatedBusinessBranchList() {
        List<AffiliatedBusinessBranch> expected = Arrays.asList(
                GENERATOR.nextObject(AffiliatedBusinessBranch.class),
                GENERATOR.nextObject(AffiliatedBusinessBranch.class)
        );

        when(affiliatedbusinessbranchCrud.findAll()).thenReturn(expected);

        List<AffiliatedBusinessBranch> result = service.getAllAffiliatedBusinessBranchList();

        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(2);
        verify(affiliatedbusinessbranchCrud).findAll();
    }

    @Test
    void getAffiliatedBusinessBranchById() {
        Integer id = 5;
        AffiliatedBusinessBranch entity = GENERATOR.nextObject(AffiliatedBusinessBranch.class);
        entity.setId(id);

        when(affiliatedbusinessbranchCrud.findById(id)).thenReturn(Optional.of(entity));

        AffiliatedBusinessBranch result = service.getAffiliatedBusinessBranchById(id);

        assertThat(result).isEqualTo(entity);
        verify(affiliatedbusinessbranchCrud).findById(id);
    }

    @Test
    void deleteAffiliatedBusinessBranch() {
        Integer id = 7;
        AffiliatedBusinessBranch entity = GENERATOR.nextObject(AffiliatedBusinessBranch.class);
        entity.setId(id);

        when(affiliatedbusinessbranchCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto resp = service.deleteAffiliatedBusinessBranch(id);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro eliminado con Exito");
        verify(affiliatedbusinessbranchCrud).delete(entity);
    }

    @Test
    void createAffiliatedBusinessBranch_success() {
        NewAffiliatedBusinessBranchDto dto = new NewAffiliatedBusinessBranchDto();
        dto.setId_affiliated_business(10);
        dto.setId_branch(20);

        AffiliatedBusinessBranch saved = new AffiliatedBusinessBranch();
        saved.setId(99);

        // No existing relation found
        when(affiliatedbusinessbranchCrud.findById_branchId_affiliated_business(dto.getId_branch(), dto.getId_affiliated_business()))
                .thenReturn(Collections.emptyList());

        // resolve affiliated business and branch
        when(affiliatedBusinessService.getAffiliatedBusinessById(dto.getId_affiliated_business()))
                .thenReturn(GENERATOR.nextObject(AffiliatedBusiness.class));

        when(branchService.getBranchById(dto.getId_branch())).thenReturn(GENERATOR.nextObject(Branch.class));

        when(affiliatedbusinessbranchCrud.save(any(AffiliatedBusinessBranch.class))).thenReturn(saved);

        ResponseSuccessfullyDto resp = service.createAffiliatedBusinessBranch(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp.getMessage()).isEqualTo("Registro creado con Exito");
        verify(affiliatedbusinessbranchCrud).save(any(AffiliatedBusinessBranch.class));
    }

    @Test
    void createAffiliatedBusinessBranch_alreadyExists_throws() {
        NewAffiliatedBusinessBranchDto dto = new NewAffiliatedBusinessBranchDto();
        dto.setId_affiliated_business(11);
        dto.setId_branch(22);

        // simulate existing relation
        when(affiliatedbusinessbranchCrud.findById_branchId_affiliated_business(dto.getId_branch(), dto.getId_affiliated_business()))
                .thenReturn(Collections.singletonList(GENERATOR.nextObject(AffiliatedBusinessBranch.class)));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.createAffiliatedBusinessBranch(dto));

        assertThat(ex.getCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ex.getMessage()).contains("afiliado");
    }

    @Test
    void updateAffiliatedBusinessBranch() {
        AffiliatedBusinessBranchDto dto = new AffiliatedBusinessBranchDto();
        dto.setId(55);
        dto.setId_affiliated_business(101);
        dto.setId_branch(202);

        AffiliatedBusinessBranch existing = GENERATOR.nextObject(AffiliatedBusinessBranch.class);
        existing.setId(dto.getId());

        when(affiliatedbusinessbranchCrud.findById(dto.getId())).thenReturn(Optional.of(existing));
        when(affiliatedBusinessService.getAffiliatedBusinessById(dto.getId_affiliated_business()))
                .thenReturn(GENERATOR.nextObject(AffiliatedBusiness.class));
        when(branchService.getBranchById(dto.getId_branch())).thenReturn(GENERATOR.nextObject(Branch.class));
        when(affiliatedbusinessbranchCrud.save(any(AffiliatedBusinessBranch.class))).thenReturn(existing);

        ResponseSuccessfullyDto resp = service.updateAffiliatedBusinessBranch(dto);

        assertThat(resp.getCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getMessage()).isEqualTo("Registro actualizado con Exito");
        verify(affiliatedbusinessbranchCrud).save(any(AffiliatedBusinessBranch.class));
    }

    @Test
    void getAffiliatedBusinessBranch_responses() {
        Integer id = 33;
        AffiliatedBusinessBranch entity = GENERATOR.nextObject(AffiliatedBusinessBranch.class);
        entity.setId(id);

        when(affiliatedbusinessbranchCrud.findById(id)).thenReturn(Optional.of(entity));

        ResponseSuccessfullyDto single = service.getAffiliatedBusinessBranch(id);
        assertThat(single.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(single.getBody()).isEqualTo(entity);

        List<AffiliatedBusinessBranch> list = Arrays.asList(entity);
        when(affiliatedbusinessbranchCrud.findAll()).thenReturn(list);
        ResponseSuccessfullyDto all = service.getAllAffiliatedBusinessBranchListResponse();
        assertThat(all.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(all.getBody()).isEqualTo(list);

        when(affiliatedbusinessbranchCrud.findById_branch(id)).thenReturn(list);
        ResponseSuccessfullyDto byBranch = service.getById_branchResponse(id);
        assertThat(byBranch.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(byBranch.getBody()).isEqualTo(list);

        when(affiliatedbusinessbranchCrud.findById_affiliated_business(id)).thenReturn(list);
        ResponseSuccessfullyDto byAffiliated = service.getById_affiliated_businessResponse(id);
        assertThat(byAffiliated.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(byAffiliated.getBody()).isEqualTo(list);
    }
}
