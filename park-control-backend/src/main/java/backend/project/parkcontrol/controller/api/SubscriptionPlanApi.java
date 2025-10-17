package backend.project.parkcontrol.controller.api;

import backend.project.parkcontrol.dto.request.NewSubscriptionPlanDto;
import backend.project.parkcontrol.dto.response.SubscriptionPlanDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/subscriptionPlans")
public interface SubscriptionPlanApi {

    // CRUD
    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> create(@RequestBody NewSubscriptionPlanDto dto);

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> update(@RequestBody SubscriptionPlanDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> delete(@PathVariable Integer id);

    // GETTERS
    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAll();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getById(@PathVariable Integer id);
}
