package backend.project.parkcontrol.controller;

import backend.project.parkcontrol.controller.api.SubscriptionPlanApi;
import backend.project.parkcontrol.dto.request.NewSubscriptionPlanDto;
import backend.project.parkcontrol.dto.response.SubscriptionPlanDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.service.SubscriptionPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SubscriptionPlanController implements SubscriptionPlanApi {

    private final SubscriptionPlanService service;

    // ==============================
    // CRUD
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> create(NewSubscriptionPlanDto dto) {
        log.info("POST /subscriptionPlans");
        ResponseSuccessfullyDto resp = service.createSubscriptionPlan(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> update(SubscriptionPlanDto dto) {
        log.info("PUT /subscriptionPlans");
        ResponseSuccessfullyDto resp = service.updateSubscriptionPlan(dto);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> delete(Integer id) {
        log.info("DELETE /subscriptionPlans/{}", id);
        ResponseSuccessfullyDto resp = service.deleteSubscriptionPlan(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }

    // ==============================
    // GETTERS
    // ==============================

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAll() {
        log.info("GET /subscriptionPlans/all");
        ResponseSuccessfullyDto resp = service.getAllSubscriptionPlanListResponse();
        return new ResponseEntity<>(resp, resp.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getById(Integer id) {
        log.info("GET /subscriptionPlans/{}", id);
        ResponseSuccessfullyDto resp = service.getSubscriptionPlan(id);
        return new ResponseEntity<>(resp, resp.getCode());
    }
}
