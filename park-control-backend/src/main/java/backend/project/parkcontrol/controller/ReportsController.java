package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.ReportsApi;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.ReportsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ReportsController implements ReportsApi {

    private final ReportsService reportsService;

    // ==========================================
    // 🔹 Reporte 1: Ocupación por sucursal
    // ==========================================
    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getOcupacionPorSucursal() {
        log.info("GET /reports/ocupacion-por-sucursal");
        ResponseSuccessfullyDto resp = reportsService.getOcupacionPorSucursal();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    // ==========================================
    // 🔹 Reporte 2: Uso de contratos y horas
    // ==========================================
    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getContractUsageReport() {
        log.info("GET /reports/uso-contratos");
        ResponseSuccessfullyDto resp = reportsService.getContractUsageReport();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    // ==========================================
    // 🔹 Reporte 3: Empresas afiliadas
    // ==========================================
    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAffiliatedBusinessReport() {
        log.info("GET /reports/empresas-afiliadas");
        ResponseSuccessfullyDto resp = reportsService.getAffiliatedBusinessReport();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    // ==========================================
    // 🔹 Reporte 4: Ingresos por tipo de periodo
    // ==========================================
    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getBranchIncomeReportByType(Integer typeReport) {
        log.info("GET /reports/ingresos?typeReport={}", typeReport);
        ResponseSuccessfullyDto resp = reportsService.getBranchIncomeReportByType(typeReport);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    // ==========================================
    // 🔹 Reporte 5: Incidentes
    // ==========================================
    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getIncidentReport() {
        log.info("GET /reports/incidentes");
        ResponseSuccessfullyDto resp = reportsService.getIncidentReport();
        return new ResponseEntity<>(resp, resp.getCode());
    }
}
