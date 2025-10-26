package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolCrud extends JpaRepository<RoleEntity, Integer> {

}
