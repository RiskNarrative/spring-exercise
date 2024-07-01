package lexisnexis.rn.springboot.repository;

import lexisnexis.rn.springboot.Entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyEntity, String> {

    Optional<CompanyEntity> findById(String companyNumber);
}
