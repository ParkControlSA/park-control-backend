package backend.project.parkcontrol.repository.crud;


import backend.project.parkcontrol.repository.entities.ValidationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidationCodeCrud extends JpaRepository<ValidationCode, Integer> {


    @Query(value = "select * from validation_code where id_user = ? ;", nativeQuery = true)
    Optional<ValidationCode> getByUser(Integer userId);


}
