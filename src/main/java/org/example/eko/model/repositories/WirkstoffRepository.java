package org.example.eko.model.repositories;

import org.example.eko.model.entities.WirkstoffAtcCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WirkstoffRepository extends JpaRepository<WirkstoffAtcCode, String> {
}
