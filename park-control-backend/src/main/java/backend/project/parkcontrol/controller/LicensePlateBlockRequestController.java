package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.LicensePlateBlockRequestApi;
import backend.project.parkcontrol.dto.request.NewLicensePlateBlockRequestDto;
import backend.project.parkcontrol.dto.response.LicensePlateBlockRequestDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.LicensePlateBlockRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LicensePlateBlockRequestController implements LicensePlateBlockRequestApi {

    private final LicensePlateBlockRequestService service;

    // ==============================
    // CRUD
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createLicensePlateBlockRequest(NewLicensePlateBlockRequestDto dto) {
        log.info("POST /licensePlateBlockRequests");
        ResponseSuccessfullyDto resp = service.createLicensePlateBlockRequest(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateLicensePlateBlockRequest(LicensePlateBlockRequestDto dto) {
        log.info("PUT /licensePlateBlockRequests");
        ResponseSuccessfullyDto resp = service.updateLicensePlateBlockRequest(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteLicensePlateBlockRequest(Integer id) {
        log.info("DELETE /licensePlateBlockRequests/{}", id);
        ResponseSuccessfullyDto resp = service.deleteLicensePlateBlockRequest(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> changeStatus(Integer id, Integer status) {
        log.info("PUT /licensePlateBlockRequests/changeStatus/{}/{}", id, status);
        ResponseSuccessfullyDto resp = service.changeStatus(id, status);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    // ==============================
    // GETTERS
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllLicensePlateBlockRequest() {
        log.info("GET /licensePlateBlockRequests/all");
        ResponseSuccessfullyDto resp = service.getAllLicensePlateBlockRequestListResponse();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllByStatus(Integer id) {
        log.info("GET /licensePlateBlockRequests/all/status/{}",id);
        ResponseSuccessfullyDto resp = service.getAllLicensePlateBlockRequestListByStatusResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getById(Integer id) {
        log.info("GET /licensePlateBlockRequests/{}", id);
        ResponseSuccessfullyDto resp = service.getLicensePlateBlockRequest(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getByContractId(Integer id) {
        log.info("GET /licensePlateBlockRequests/contract/{}", id);
        ResponseSuccessfullyDto resp = service.getById_contractResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getByAssignedUserId(Integer id) {
        log.info("GET /licensePlateBlockRequests/assigned/{}", id);
        ResponseSuccessfullyDto resp = service.getById_assignedResponse(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }
}
