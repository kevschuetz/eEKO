package org.example.eko.model.repositories.staging;

import org.example.eko.model.entities.staging.DateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateRepository extends JpaRepository<DateEntity, Long> {
}
