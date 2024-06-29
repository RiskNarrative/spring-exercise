package lexisnexis.rn.springboot.repository;

import lexisnexis.rn.springboot.Entity.OfficerEntity;
import lexisnexis.rn.springboot.model.Officer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficerRepository extends JpaRepository<OfficerEntity, Long> {


}
