package backend.project.parkcontrol.repository.crud;

import backend.project.parkcontrol.repository.entities.AffiliateCommerceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffiliateCommerceCrud extends JpaRepository<AffiliateCommerceEntity, Integer> {

}
