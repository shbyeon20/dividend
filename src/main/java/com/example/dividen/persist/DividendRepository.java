package com.example.dividen.persist;

import com.example.dividen.persist.entity.DividendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DividendRepository extends JpaRepository<DividendEntity,Long> {

    Optional<List<DividendEntity>> findAllByCompanyId(Long id);
}
