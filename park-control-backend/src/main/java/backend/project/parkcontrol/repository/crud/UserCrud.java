package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.BranchTemporaryPermit;
import backend.project.parkcontrol.repository.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCrud extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
    Optional<UserEntity> getUserByUsername(String username);

    @Query(value = "select * from user where id_rol = ?", nativeQuery = true)
    List<UserEntity> findById_role(Integer id_rol);
}
