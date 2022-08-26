package org.example.eko.model.repositories;

import org.example.eko.model.entities.EkoEintrag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EkoEintragRepository extends JpaRepository<EkoEintrag, Long> {
}
