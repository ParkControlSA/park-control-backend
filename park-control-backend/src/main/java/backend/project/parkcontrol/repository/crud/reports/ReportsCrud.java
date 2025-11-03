package backend.project.parkcontrol.repository.crud.reports;

import backend.project.parkcontrol.repository.entities.AffiliatedBusinessBranch;
import backend.project.parkcontrol.repository.entities.Branch;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportsCrud extends JpaRepository<Branch, Integer> {
    // Reporte 1
    @Query(value = "SELECT \n" +
            "    b.id AS branch_id,\n" +
            "    b.name AS branch_name,\n" +
            "    b.2r_capacity,\n" +
            "    b.4r_capacity,\n" +
            "    b.2r_ocupation,\n" +
            "    b.4r_ocupation,\n" +
            "    ROUND((b.2r_ocupation / b.2r_capacity) * 100, 2) AS twoRPercent,\n" +
            "    ROUND((b.4r_ocupation / b.4r_capacity) * 100, 2) AS fourRPercent\n" +
            "FROM branch b;", nativeQuery = true)
    List<Object[]> findOcupacionPorSucursal();;

    // 🔹 Reporte 2
    @Query(value = "SELECT \n" +
            "    u.id AS user_id,\n" +
            "    CONCAT(u.name) AS user_name,\n" +
            "    c.id AS contract_id,\n" +
            "    sp.name AS plan_name,\n" +
            "    c.active AS is_active,\n" +
            "    SUM(ch.consumed_hours) AS total_consumed_hours,\n" +
            "    SUM(ch.included_hours) AS total_included_hours,\n" +
            "    (SUM(ch.included_hours) - SUM(ch.consumed_hours)) AS available_balance,\n" +
            "    CASE \n" +
            "        WHEN SUM(ch.consumed_hours) > SUM(ch.included_hours)\n" +
            "        THEN SUM(ch.consumed_hours) - SUM(ch.included_hours)\n" +
            "        ELSE 0\n" +
            "    END AS exceeded_hours\n" +
            "FROM contract c\n" +
            "JOIN user u ON c.id_user = u.id\n" +
            "JOIN subscription_plan sp ON c.id_plan = sp.id\n" +
            "LEFT JOIN contract_history ch ON ch.id_contract = c.id\n" +
            "GROUP BY u.id, u.name, c.id, sp.name, c.active;", nativeQuery = true)
    List<Object[]> getContractUsageReport();

    // Reporte 3
    @Query(value = "SELECT \n" +
            "    ab.id AS business_id,\n" +
            "    ab.business_name,\n" +
            "    b.name AS branch_name,\n" +
            "    COUNT(DISTINCT t.id) AS tickets_benefited,\n" +
            "    SUM(tbu.granted_hours) AS total_free_hours,\n" +
            "    ROUND(SUM(tbu.granted_hours * tu.hourly_rate), 2) AS total_to_liquidate\n" +
            "FROM ticket_business_usage tbu\n" +
            "JOIN ticket_usage tu ON tbu.id_ticket_usage = tu.id\n" +
            "JOIN ticket t ON tu.id_ticket = t.id\n" +
            "JOIN branch b ON t.id_branch = b.id\n" +
            "JOIN affiliated_business ab ON tbu.id_affiliated_business = ab.id\n" +
            "GROUP BY ab.id, ab.business_name, b.name;", nativeQuery = true)
    List<Object[]> getAffiliatedBusinessReport();

    // Reporte 4: Ingresos diarios por sucursal
    @Query(value ="SELECT \n" +
            "    b.name AS branch_name,\n" +
            "    DATE(tp.date) AS report_date,\n" +
            "    SUM(tp.total_amount) AS total_income,\n" +
            "    SUM(CASE \n" +
            "            WHEN c.id IS NULL THEN tp.total_amount \n" +
            "            ELSE 0 \n" +
            "        END) AS non_subscriber_income,\n" +
            "    SUM(CASE \n" +
            "            WHEN c.id IS NOT NULL THEN tp.total_amount \n" +
            "            ELSE 0 \n" +
            "        END) AS subscriber_income,\n" +
            "    COUNT(tp.id) AS total_transactions\n" +
            "FROM ticket_payment tp\n" +
            "JOIN ticket t ON tp.id_ticket = t.id\n" +
            "JOIN branch b ON t.id_branch = b.id\n" +
            "LEFT JOIN contract c ON t.plate = c.license_plate\n" +
            "GROUP BY b.name, DATE(tp.date)\n" +
            "ORDER BY report_date DESC; ", nativeQuery = true)
    List<Object[]> getDailyBranchIncomeReport();

    //Reporte 4: 1. Diario, 2. Semanal, 3. Mensual, 4. Anual

    @Query(value = """
    SELECT 
        b.name AS branch_name,
        MIN(
            CASE 
                WHEN :typeReport = 1 THEN CURDATE()
                WHEN :typeReport = 2 THEN DATE(tp.date)
                WHEN :typeReport = 3 THEN DATE_FORMAT(tp.date, '%Y-%m-01')
                WHEN :typeReport = 4 THEN CONCAT(YEAR(tp.date), '-01-01')
            END
        ) AS init_date,
        MAX(
            CASE 
                WHEN :typeReport = 1 THEN CURDATE()
                WHEN :typeReport = 2 THEN DATE(tp.date)
                WHEN :typeReport = 3 THEN LAST_DAY(tp.date)
                WHEN :typeReport = 4 THEN CONCAT(YEAR(tp.date), '-12-31')
            END
        ) AS last_date,
        :typeReport AS type_report,
        SUM(tp.total_amount) AS total_income,
        SUM(CASE WHEN c.id IS NULL THEN tp.total_amount ELSE 0 END) AS non_subscriber_income,
        SUM(CASE WHEN c.id IS NOT NULL THEN tp.total_amount ELSE 0 END) AS subscriber_income,
        COUNT(tp.id) AS total_transactions
    FROM ticket_payment tp
    JOIN ticket t ON tp.id_ticket = t.id
    JOIN branch b ON t.id_branch = b.id
    LEFT JOIN contract c ON t.plate = c.license_plate
    WHERE (
        (:typeReport = 1 AND DATE(tp.date) = CURDATE()) OR
        (:typeReport = 2 AND YEARWEEK(tp.date) = YEARWEEK(CURDATE())) OR
        (:typeReport = 3 AND MONTH(tp.date) = MONTH(CURDATE()) AND YEAR(tp.date) = YEAR(CURDATE())) OR
        (:typeReport = 4 AND YEAR(tp.date) = YEAR(CURDATE()))
    )
    GROUP BY b.name
    ORDER BY b.name
    """, nativeQuery = true)
    List<Object[]> findBranchIncomeByTime(@Param("typeReport") Integer typeReport);

    // Reporte 5: Incidentes
    @Query(value = "SELECT \n" +
            "    i.id AS incident_id,\n" +
            "    b.name AS branch_name,\n" +
            "    t.plate AS vehicle_plate,\n" +
            "    i.incident_type,\n" +
            "    i.description,\n" +
            "    i.evidence_url,\n" +
            "    i.status,\n" +
            "    i.date,\n" +
            "    CONCAT(u.name) AS manager_name\n" +
            "FROM incident i\n" +
            "JOIN ticket t ON i.id_ticket = t.id\n" +
            "JOIN branch b ON t.id_branch = b.id\n" +
            "LEFT JOIN user u ON i.id_user_manager = u.id\n" +
            "ORDER BY i.date DESC;", nativeQuery = true)
    List<Object[]> getIncidentReport();
}
