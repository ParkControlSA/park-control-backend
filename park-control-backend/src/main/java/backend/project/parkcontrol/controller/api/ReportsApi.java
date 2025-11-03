package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reports")
public interface ReportsApi {

    // ==========================================
    // Reporte 1: Ocupación por sucursal
    // ==========================================
    @GetMapping("/ocupacion-por-sucursal")
    ResponseEntity<ResponseSuccessfullyDto> getOcupacionPorSucursal();

    // ==========================================
    // Reporte 2: Uso de contratos y horas
    // ==========================================
    @GetMapping("/uso-contratos")
    ResponseEntity<ResponseSuccessfullyDto> getContractUsageReport();

    // ==========================================
    // Reporte 3: Empresas afiliadas
    // ==========================================
    @GetMapping("/empresas-afiliadas")
    ResponseEntity<ResponseSuccessfullyDto> getAffiliatedBusinessReport();

    // ==========================================
    // Reporte 4: Ingresos por tipo de periodo
    // ==========================================
    @GetMapping("/ingresos/{typeReport}")
    ResponseEntity<ResponseSuccessfullyDto> getBranchIncomeReportByType(@PathVariable Integer typeReport);

    // ==========================================
    // Reporte 5: Incidentes
    // ==========================================
    @GetMapping("/incidentes")
    ResponseEntity<ResponseSuccessfullyDto> getIncidentReport();
}
