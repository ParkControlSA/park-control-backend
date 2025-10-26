package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.NewIncidentDto;
import backend.project.parkcontrol.dto.response.IncidentDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/incident")
public interface IncidentApi {

    // CRUD
    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createIncident(@RequestBody NewIncidentDto dto);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateIncident(@RequestBody IncidentDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteIncident(@PathVariable Integer id);

    // GETTERS
    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllIncidents();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getIncidentById(@PathVariable Integer id);

    @GetMapping("/ticket/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getIncidentsByTicketId(@PathVariable Integer id);

    @GetMapping("/user/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getIncidentsByUserManagerId(@PathVariable Integer id);
}
