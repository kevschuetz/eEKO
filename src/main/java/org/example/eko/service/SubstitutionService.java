package org.example.eko.service;

import org.example.eko.model.dtos.MedikamentEkoDTO;
import org.example.eko.model.entities.DateEntity;
import org.example.eko.model.entities.Medikament;
import org.example.eko.model.entities.WirkstoffAtcCode;
import org.example.eko.model.entities.Wirkstoff;
import org.example.eko.model.repositories.MedikamentRepository;
import org.example.eko.model.repositories.WirkstoffRepository;
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
        return null;
    }

}
