package org.example.eko.model.repositories;

import org.example.eko.model.entities.DateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateTimeRepository extends JpaRepository<DateEntity, Long> {
}
