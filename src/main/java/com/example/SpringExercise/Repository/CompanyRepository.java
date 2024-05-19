package com.example.SpringExercise.Repository;

import com.example.SpringExercise.Entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, String> {
}
