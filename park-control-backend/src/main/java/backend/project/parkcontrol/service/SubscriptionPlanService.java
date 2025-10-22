package backend.project.parkcontrol.service;

import backend.project.parkcontrol.dto.request.NewSubscriptionPlanDto;
import backend.project.parkcontrol.dto.response.SubscriptionPlanDto;
import backend.project.parkcontrol.dto.response.ResponseSuccessfullyDto;
import backend.project.parkcontrol.exception.BusinessException;
import backend.project.parkcontrol.repository.crud.SubscriptionPlanCrud;
import backend.project.parkcontrol.repository.entities.SubscriptionPlan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubscriptionPlanService {

    private final SubscriptionPlanCrud subscriptionPlanCrud;

    // ==============================
    // GETTERS
    // ==============================

    public List<SubscriptionPlan> getAllSubscriptionPlanList() {
        List<SubscriptionPlan> list = subscriptionPlanCrud.findAll();
        return list;
    }

    public SubscriptionPlan getSubscriptionPlanById(Integer id) {
        return subscriptionPlanCrud.findById(id).get();
    }

    // ==============================
    // CRUD Methods
    // ==============================

    public ResponseSuccessfullyDto createSubscriptionPlan(NewSubscriptionPlanDto dto) {
        SubscriptionPlan e = new SubscriptionPlan();
        e.setName(dto.getName());
        e.setMonth_hours(dto.getMonth_hours());
        e.setDaily_hours(dto.getDaily_hours());
        e.setTotal_discount(dto.getTotal_discount());
        e.setAnnual_discount(dto.getAnnual_discount());
        try {
            subscriptionPlanCrud.save(e);

            return ResponseSuccessfullyDto.builder()
                    .code(HttpStatus.CREATED)
                    .message("Registro creado con Éxito")
                    .build();
        } catch (DataIntegrityViolationException ex) {
            Throwable root = ExceptionUtils.getRootCause(ex);
            if (root.getMessage().contains("SQLSTATE 45000")) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, root.getMessage());
            }
            throw ex;
        }

    }

    public ResponseSuccessfullyDto updateSubscriptionPlan(SubscriptionPlanDto dto) {
        SubscriptionPlan existing = getSubscriptionPlanById(dto.getId());
        existing.setName(dto.getName());
        existing.setMonth_hours(dto.getMonth_hours());
        existing.setDaily_hours(dto.getDaily_hours());
        existing.setTotal_discount(dto.getTotal_discount());
        existing.setAnnual_discount(dto.getAnnual_discount());

        try {
            subscriptionPlanCrud.save(existing);

            return ResponseSuccessfullyDto.builder()
                    .code(HttpStatus.ACCEPTED)
                    .message("Registro actualizado con Éxito")
                    .build();
        }  catch (DataIntegrityViolationException ex) {
            Throwable root = ExceptionUtils.getRootCause(ex);
            if (root.getMessage().contains("SQLSTATE 45000")) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, root.getMessage());
            }
            throw ex;
        }
    }

    public ResponseSuccessfullyDto deleteSubscriptionPlan(Integer id) {
        SubscriptionPlan entity = getSubscriptionPlanById(id);
        subscriptionPlanCrud.delete(entity);
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.ACCEPTED)
                .message("Registro eliminado con Éxito")
                .build();
    }

    // ==============================
    // ResponseSuccessfullyDto Getters
    // ==============================

    public ResponseSuccessfullyDto getSubscriptionPlan(Integer id) {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registro encontrado con Éxito")
                .body(getSubscriptionPlanById(id))
                .build();
    }

    public ResponseSuccessfullyDto getAllSubscriptionPlanListResponse() {
        return ResponseSuccessfullyDto.builder()
                .code(HttpStatus.FOUND)
                .message("Registros encontrados con Éxito")
                .body(getAllSubscriptionPlanList())
                .build();
    }
}

