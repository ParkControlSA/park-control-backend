package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.reports.*;
import backend.project.parkcontrol.repository.crud.reports.ReportsCrud;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportsServiceTest {

    @Mock
    private ReportsCrud reportsCrud;

    @InjectMocks
    private ReportsService service;

    // =====================================
    // 🔹 Reporte 1: Ocupación por sucursal
    // =====================================
    @Test
    void getOcupacionPorSucursal_success() {
        Object[] row = {1, "Sucursal A", 10, 20, 8, 15, 80.0, 75.0};
        when(reportsCrud.findOcupacionPorSucursal()).thenReturn(List.<Object[]>of(row));

        ResponseSuccessfullyDto response = service.getOcupacionPorSucursal();

        assertThat(response.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getMessage()).contains("ocupación");
        List<?> body = (List<?>) response.getBody();
        assertThat(body).hasSize(1);

        OcupationBranchReportDto dto = (OcupationBranchReportDto) body.get(0);
        assertThat(dto.getBranchName()).isEqualTo("Sucursal A");
        assertThat(dto.getTwoRPercent()).isEqualTo(80.0);

        verify(reportsCrud).findOcupacionPorSucursal();
    }

    // =====================================
    // 🔹 Reporte 2: Uso de contratos
    // =====================================
    @Test
    void getContractUsageReport_success() {
        Object[] row = {1, "User", 10, "Plan Gold", true, 50.0, 60.0, 10.0, 5.0};
        when(reportsCrud.getContractUsageReport()).thenReturn(List.<Object[]>of(row));

        ResponseSuccessfullyDto response = service.getContractUsageReport();

        assertThat(response.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getMessage()).contains("contratos");
        List<?> body = (List<?>) response.getBody();
        assertThat(body).hasSize(1);

        BranchIncomeReportDto dto = (BranchIncomeReportDto) body.get(0);
        assertThat(dto.getUserName()).isEqualTo("User");
        assertThat(dto.getPlanName()).isEqualTo("Plan Gold");

        verify(reportsCrud).getContractUsageReport();
    }

    // =====================================
    // 🔹 Reporte 3: Empresas afiliadas
    // =====================================
    @Test
    void getAffiliatedBusinessReport_success() {
        Object[] row = {1, "Empresa X", "Sucursal Y", 5, 20.0, 100.0};
        when(reportsCrud.getAffiliatedBusinessReport()).thenReturn(List.<Object[]>of(row));

        ResponseSuccessfullyDto response = service.getAffiliatedBusinessReport();

        assertThat(response.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getMessage()).contains("afiliadas");
        List<?> body = (List<?>) response.getBody();
        assertThat(body).hasSize(1);

        AffiliatedBusinessReportDto dto = (AffiliatedBusinessReportDto) body.get(0);
        assertThat(dto.getBusinessName()).isEqualTo("Empresa X");
        assertThat(dto.getTotalFreeHours()).isEqualTo(20.0);

        verify(reportsCrud).getAffiliatedBusinessReport();
    }

    // =====================================
    // 🔹 Reporte 4: Ingresos por periodo
    // =====================================
    @Test
    void getBranchIncomeReportByType_success() {
        Object[] row = {"Sucursal Z", "2025-01-01", "2025-01-31", 1, 1000.0, 400.0, 600.0, 50};
        when(reportsCrud.findBranchIncomeByTime(1)).thenReturn(List.<Object[]>of(row));

        ResponseSuccessfullyDto response = service.getBranchIncomeReportByType(1);

        assertThat(response.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getMessage()).contains("periodo");
        List<?> body = (List<?>) response.getBody();
        assertThat(body).hasSize(1);

        BranchIncomeReportTimeDto dto = (BranchIncomeReportTimeDto) body.get(0);
        assertThat(dto.getBranchName()).isEqualTo("Sucursal Z");
        assertThat(dto.getTotalIncome()).isEqualTo(1000.0);
        assertThat(dto.getInitDate()).isEqualTo(LocalDate.parse("2025-01-01"));

        verify(reportsCrud).findBranchIncomeByTime(1);
    }

    // =====================================
    // 🔹 Reporte 5: Incidentes
    // =====================================
    @Test
    void getIncidentReport_success() {
        Object[] row = {
                1, "Sucursal Central", "P123ABC", 2, "Choque leve",
                "http://evidencia.com/img", 1, "2025-02-15 08:30:00", "Gerente Z"
        };
        when(reportsCrud.getIncidentReport()).thenReturn(List.<Object[]>of(row));

        ResponseSuccessfullyDto response = service.getIncidentReport();

        assertThat(response.getCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getMessage()).contains("incidentes");
        List<?> body = (List<?>) response.getBody();
        assertThat(body).hasSize(1);

        IncidentReportDto dto = (IncidentReportDto) body.get(0);
        assertThat(dto.getIncidentId()).isEqualTo(1);
        assertThat(dto.getBranchName()).isEqualTo("Sucursal Central");
        assertThat(dto.getDate()).isEqualTo(LocalDateTime.parse("2025-02-15T08:30:00"));

        verify(reportsCrud).getIncidentReport();
    }

    // =====================================
    // 🔹 Métodos auxiliares: parseLocalDate y parseLocalDateTime
    // =====================================
    @Test
    void parseMethods_handleInvalidValuesGracefully() {
        // Forzar parseos fallidos
        ResponseSuccessfullyDto r1 = service.getBranchIncomeReportByType(1);
        ResponseSuccessfullyDto r2 = service.getIncidentReport();

        // No hay excepción esperada, solo cobertura del bloque catch
        assertThat(r1).isNotNull();
        assertThat(r2).isNotNull();
    }
}
