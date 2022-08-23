package org.example.eko.model.repositories;

import org.example.eko.model.entities.Wirkstoff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WirkstoffInformationRepository extends JpaRepository<Wirkstoff, Long> {
}
