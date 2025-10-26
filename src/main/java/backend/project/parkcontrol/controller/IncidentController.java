package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.IncidentApi;
import backend.project.parkcontrol.dto.request.NewIncidentDto;
import backend.project.parkcontrol.dto.response.IncidentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.IncidentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class IncidentController implements IncidentApi {

    private final IncidentService incidentService;

    // ==============================
    // CRUD
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createIncident(NewIncidentDto dto) {
        log.info("POST /incident");
        ResponseSuccessfullyDto resp = incidentService.createIncident(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateIncident(IncidentDto dto) {
        log.info("PUT /incident");
        ResponseSuccessfullyDto resp = incidentService.updateIncident(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteIncident(Integer id) {
        log.info("DELETE /incident/{}", id);
        ResponseSuccessfullyDto resp = incidentService.deleteIncident(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    // ==============================
    // GETTERS
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllIncidents() {
        log.info("GET /incident/all");
        ResponseSuccessfullyDto resp = incidentService.getAllIncidentListResponse();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getIncidentById(Integer id) {
        log.info("GET /incident/{}", id);
        ResponseSuccessfullyDto resp = incidentService.getIncident(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getIncidentsByTicketId(Integer id) {
        log.info("GET /incident/ticket/{}", id);
        ResponseSuccessfullyDto resp = incidentService.getById_ticketResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getIncidentsByUserManagerId(Integer id) {
        log.info("GET /incident/user/{}", id);
        ResponseSuccessfullyDto resp = incidentService.getById_user_managerResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }
}
