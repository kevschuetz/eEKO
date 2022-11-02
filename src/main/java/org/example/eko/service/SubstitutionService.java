package org.example.eko.service;

import org.example.eko.model.dtos.MedikamentEkoDTO;
import org.example.eko.model.entities.staging.Medikament;
import org.example.eko.model.repositories.staging.MedikamentRepository;
import org.example.eko.model.repositories.staging.WirkstoffRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SubstitutionService {

    private final MedikamentRepository medikamentRepository;
    private final WirkstoffRepository wirkstoffRepository;

    public SubstitutionService(MedikamentRepository medikamentRepository, WirkstoffRepository wirkstoffRepository){
        this.medikamentRepository = medikamentRepository;
        this.wirkstoffRepository = wirkstoffRepository;
    }

    @Transactional
    public List<MedikamentEkoDTO> getSubstitutesOrdered(String pharmaNummer, LocalDate date){
        Medikament medikament;
        var optMed = medikamentRepository.findByPharmaNummer(pharmaNummer)
                .stream()
                .filter(m -> m.getValidFrom().getDate().compareTo(date) <= 0)
                .max((m1, m2) -> m1.getValidFrom().getDate().compareTo(m2.getValidFrom().getDate()));
        medikament = optMed.isPresent() ? optMed.get() : null;
        if(medikament == null) return null;

        var list = medikament.getMedikamentVergleichsEntityList()
                .stream()
                .map(v -> new MedikamentEkoDTO(v.getVergleichsMedikament(), v.getVergleichsKennzeichen()))
                .collect(Collectors.toList());

        list.add(new MedikamentEkoDTO(medikament, 1));
        return list;
    }
}
