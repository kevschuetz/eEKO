package org.example.eko.model.repositories.staging;

import org.example.eko.model.entities.staging.Medikament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MedikamentRepository extends JpaRepository<Medikament, String> {

    @Query(nativeQuery = true,
    value = "SELECT * FROM staging_area.eko_eintrag WHERE PHARMA_NUMMER = ?")
    Collection<Medikament> findByPharmaNummer(String pharmaNummer);


    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM staging_area.eko_eintrag m1 JOIN staging_area.date d1 ON m1.VALID_DATE_ID = d1.DATE_ID " +
                    "WHERE d1.DATE <= ? " +
                    "AND NOT EXISTS (" +
                    "   SELECT * " +
                    "   FROM staging_area.eko_eintrag m2 JOIN staging_area.eko_eintrag d2 ON m2.VALID_DATE_ID = d2.DATE_ID" +
                    "   WHERE d2.DATE > d1.DATE AND m1.PHARMA_NUMMER = m2.PHARMA_NUMMER " +
                    ")")
    public Collection<Medikament> findByValidDate(String date);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM eko_eintrag m1 JOIN date d1 ON m1.VALID_DATE_ID = d1.DATE_ID " +
                    "WHERE d1.DATE_ID = ? ")
    public Collection<Medikament> findByValidDateID(long id);


}
