package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.dto.response.reports.*;
import backend.project.parkcontrol.repository.crud.reports.ReportsCrud;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportsService {

    private final ReportsCrud reportsCrud;

    // ==========================================
    // 🔹 Reporte 1: Ocupación por sucursal
    // ==========================================
    public ResponseSuccessfullyDto getOcupacionPorSucursal() {
        List<OcupationBranchReportDto> list = reportsCrud.findOcupacionPorSucursal().stream()
                .map(r -> OcupationBranchReportDto.builder()
                        .id(((Number) r[0]).intValue())
                        .branchName((String) r[1])
                        .twoRCapacity(((Number) r[2]).intValue())
                        .fourRCapacity(((Number) r[3]).intValue())
                        .twoROcupation(((Number) r[4]).intValue())
                        .fourROcupation(((Number) r[5]).intValue())
                        .twoRPercent(((Number) r[6]).doubleValue())
                        .fourRPercent(((Number) r[7]).doubleValue())
                        .build())
                .collect(Collectors.toList());

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Reporte de ocupación por sucursal obtenido con éxito")
                .body(list)
                .build();
    }

    // ==========================================
    // 🔹 Reporte 2: Uso de contratos y horas
    // ==========================================
    public ResponseSuccessfullyDto getContractUsageReport() {
        List<BranchIncomeReportDto> list = reportsCrud.getContractUsageReport().stream()
                .map(r -> BranchIncomeReportDto.builder()
                        .userId(((Number) r[0]).intValue())
                        .userName((String) r[1])
                        .contractId(((Number) r[2]).intValue())
                        .planName((String) r[3])
                        .isActive((Boolean) r[4])
                        .totalConsumedHours(r[5] != null ? ((Number) r[5]).doubleValue() : 0.0)
                        .totalIncludedHours(r[6] != null ? ((Number) r[6]).doubleValue() : 0.0)
                        .availableBalance(r[7] != null ? ((Number) r[7]).doubleValue() : 0.0)
                        .exceededHours(r[8] != null ? ((Number) r[8]).doubleValue() : 0.0)
                        .build())
                .collect(Collectors.toList());

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Reporte de uso de contratos obtenido con éxito")
                .body(list)
                .build();
    }

    // ==========================================
    // 🔹 Reporte 3: Empresas afiliadas
    // ==========================================
    public ResponseSuccessfullyDto getAffiliatedBusinessReport() {
        List<AffiliatedBusinessReportDto> list = reportsCrud.getAffiliatedBusinessReport().stream()
                .map(r -> AffiliatedBusinessReportDto.builder()
                        .businessId(((Number) r[0]).intValue())
                        .businessName((String) r[1])
                        .branchName((String) r[2])
                        .ticketsBenefited(((Number) r[3]).intValue())
                        .totalFreeHours(r[4] != null ? ((Number) r[4]).doubleValue() : 0.0)
                        .totalToLiquidate(r[5] != null ? ((Number) r[5]).doubleValue() : 0.0)
                        .build())
                .collect(Collectors.toList());

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Reporte de empresas afiliadas obtenido con éxito")
                .body(list)
                .build();
    }

    // ==========================================
    // 🔹 Reporte 4: Ingresos por tipo de periodo
    // ==========================================
    public ResponseSuccessfullyDto getBranchIncomeReportByType(Integer typeReport) {
        List<BranchIncomeReportTimeDto> list = reportsCrud.findBranchIncomeByTime(typeReport).stream()
                .map(r -> BranchIncomeReportTimeDto.builder()
                        .branchName((String) r[0])
                        .initDate(parseLocalDate(r[1]))
                        .lastDate(parseLocalDate(r[2]))
                        .typeReport(((Number) r[3]).intValue())
                        .totalIncome(r[4] != null ? ((Number) r[4]).doubleValue() : 0.0)
                        .nonSubscriberIncome(r[5] != null ? ((Number) r[5]).doubleValue() : 0.0)
                        .subscriberIncome(r[6] != null ? ((Number) r[6]).doubleValue() : 0.0)
                        .totalTransactions(r[7] != null ? ((Number) r[7]).intValue() : 0)
                        .build())
                .collect(Collectors.toList());

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Reporte de ingresos por periodo obtenido con éxito")
                .body(list)
                .build();
    }

    // ==========================================
    // 🔹 Reporte 5: Incidentes
    // ==========================================
    public ResponseSuccessfullyDto getIncidentReport() {
        List<IncidentReportDto> list = reportsCrud.getIncidentReport().stream()
                .map(r -> IncidentReportDto.builder()
                        .incidentId(((Number) r[0]).intValue())
                        .branchName((String) r[1])
                        .vehiclePlate((String) r[2])
                        .incidentType(((Number) r[3]).intValue())
                        .description((String) r[4])
                        .evidenceUrl((String) r[5])
                        .status(((Number) r[6]).intValue())
                        .date(parseLocalDateTime(r[7]))
                        .managerName((String) r[8])
                        .build())
                .collect(Collectors.toList());

        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Reporte de incidentes obtenido con éxito")
                .body(list)
                .build();
    }

    // ==========================================
    // Métodos auxiliares
    // ==========================================

    private LocalDate parseLocalDate(Object value) {
        if (value == null) return null;
        try {
            return LocalDate.parse(value.toString());
        } catch (Exception e) {
            log.warn("Error parseando LocalDate: {}", value);
            return null;
        }
    }

    private LocalDateTime parseLocalDateTime(Object value) {
        if (value == null) return null;
        try {
            return LocalDateTime.parse(value.toString().replace(" ", "T"));
        } catch (Exception e) {
            log.warn("Error parseando LocalDateTime: {}", value);
            return null;
        }
    }
}
