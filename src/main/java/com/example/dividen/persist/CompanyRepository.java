package com.example.dividen.persist;

import com.example.dividen.persist.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity,Long> {
    boolean existsByTicker(String ticker);

}
