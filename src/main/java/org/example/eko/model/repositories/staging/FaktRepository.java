package org.example.eko.model.repositories.staging;

import org.example.eko.model.entities.datamart.VergleichsFakt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaktRepository extends JpaRepository<VergleichsFakt, Long> {
}
