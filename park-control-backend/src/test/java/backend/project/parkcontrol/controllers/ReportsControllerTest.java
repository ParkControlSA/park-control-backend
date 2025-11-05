package backend.project.parkcontrol.controllers;

import backend.project.parkcontrol.controller.ReportsController;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.ReportsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportsControllerTest {

    @Mock
    private ReportsService reportsService;

    @InjectMocks
    private ReportsController controller;

    // =====================================================
    // 🔹 Reporte 1: Ocupación por sucursal
    // =====================================================
    @Test
    void getOcupacionPorSucursal_shouldReturnOkResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Reporte de ocupación por sucursal generado correctamente")
                .body(null)
                .build();

        when(reportsService.getOcupacionPorSucursal()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getOcupacionPorSucursal();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(reportsService).getOcupacionPorSucursal();
    }

    // =====================================================
    // 🔹 Reporte 2: Uso de contratos y horas
    // =====================================================
    @Test
    void getContractUsageReport_shouldReturnOkResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Reporte de uso de contratos generado correctamente")
                .body(null)
                .build();

        when(reportsService.getContractUsageReport()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getContractUsageReport();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(reportsService).getContractUsageReport();
    }

    // =====================================================
    // 🔹 Reporte 3: Empresas afiliadas
    // =====================================================
    @Test
    void getAffiliatedBusinessReport_shouldReturnOkResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Reporte de empresas afiliadas generado correctamente")
                .body(null)
                .build();

        when(reportsService.getAffiliatedBusinessReport()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getAffiliatedBusinessReport();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(reportsService).getAffiliatedBusinessReport();
    }

    // =====================================================
    // 🔹 Reporte 4: Ingresos por tipo de periodo
    // =====================================================
    @Test
    void getBranchIncomeReportByType_shouldReturnOkResponse() {
        Integer typeReport = 1;
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Reporte de ingresos por tipo generado correctamente")
                .body(null)
                .build();

        when(reportsService.getBranchIncomeReportByType(typeReport)).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getBranchIncomeReportByType(typeReport);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(reportsService).getBranchIncomeReportByType(typeReport);
    }

    // =====================================================
    // 🔹 Reporte 5: Incidentes
    // =====================================================
    @Test
    void getIncidentReport_shouldReturnOkResponse() {
        ResponseSuccessfullyDto response = ResponseSuccessfullyDto.builder()
                .code(HttpStatus.OK)
                .message("Reporte de incidentes generado correctamente")
                .body(null)
                .build();

        when(reportsService.getIncidentReport()).thenReturn(response);

        ResponseEntity<ResponseSuccessfullyDto> result = controller.getIncidentReport();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(reportsService).getIncidentReport();
    }
}
