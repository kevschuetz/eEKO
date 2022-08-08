package org.example.eko.model.repositories;

import org.example.eko.model.entities.Medikament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MedikamentRepository extends JpaRepository<Medikament, String> {

    @Query(nativeQuery = true,
    value = "SELECT * FROM FT_MEDIKAMENT WHERE PHARMA_NUMMER = ?")
    public Collection<Medikament> findByPharmaNummer(String pharmaNummer);


    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM FT_MEDIKAMENT m1 JOIN DT_DATE d1 ON m1.VALID_DATE_ID = d1.DATE_ID " +
                    "WHERE d1.DATE <= ? " +
                    "AND NOT EXISTS (" +
                    "   SELECT * " +
                    "   FROM FT_MEDIKAMENT m2 JOIN DT_DATE d2 ON m2.VALID_DATE_ID = d2.DATE_ID" +
                    "   WHERE d2.DATE > d1.DATE " +
                    ")")
    public Collection<Medikament> getValidMedikamentsByDate(String date);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM FT_MEDIKAMENT m1 JOIN DT_DATE d1 ON m1.VALID_DATE_ID = d1.DATE_ID " +
                    "WHERE d1.DATE_ID = ? ")
    public Collection<Medikament> getMedikamentsByValidDateID(long id);
}
