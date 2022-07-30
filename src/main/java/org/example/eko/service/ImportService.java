package org.example.eko.service;

import org.example.eko.model.filerepresentations.Hinweis;
import org.example.eko.model.filerepresentations.Regeltext;
import org.example.eko.model.entities.Medikament;
import org.example.eko.model.entities.WirkstoffAtcCode;
import org.example.eko.model.entities.WirkstoffInformation;
import org.example.eko.model.repositories.MedikamentRepository;
import org.example.eko.model.repositories.WirkstoffRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImportService {
    private static Logger logger = LoggerFactory.getLogger(ImportService.class);

    private final MedikamentRepository medikamentRepository;
    private final WirkstoffRepository wirkstoffRepository;

    public ImportService(MedikamentRepository medikamentRepository, WirkstoffRepository wirkstoffRepository){
        this.medikamentRepository = medikamentRepository;
        this.wirkstoffRepository = wirkstoffRepository;
    }

    public List<Medikament> importMedikamente(List<Medikament> unimportedMedikamente){
       return medikamentRepository.saveAll(unimportedMedikamente);
    }

    public List<Medikament> addHinweise(List<Medikament> medikaments, List<Hinweis> hinweise){
        for(Hinweis h : hinweise){
            medikaments.stream().filter(m -> m.getPharmaNummer().equals(h.getPharmaNummer())).findFirst().get().setHinweis(h.getHinweis());
        }

        return medikamentRepository.saveAll(medikaments);
    }

    public List<Medikament> addRegeltexte(List<Medikament> medikaments, List<Regeltext> regeltexts){
        for(Regeltext r : regeltexts){
            medikaments.stream().filter(m -> m.getPharmaNummer().equals(r.getPharmaNummer())).findFirst().get().setRegeltext(r.getRegeltext());
        }

        return medikamentRepository.saveAll(medikaments);
    }

    public List<WirkstoffAtcCode> addWirkstoffe(List<WirkstoffAtcCode> wirkstoffe) {
        return wirkstoffRepository.saveAll(wirkstoffe);
    }

    public List<Medikament> addWirkstoffInformationen(List<Medikament> importedMedikamente, List<WirkstoffInformation> wirkstoffInformationen) {
       wirkstoffInformationen.stream().collect(Collectors.groupingBy(w -> w.getPharmaNummer())).forEach((k,v)->{
           importedMedikamente.stream().filter(m -> m.getPharmaNummer().equals(k)).findFirst().get().addWirkstoffInformationen(v);
       });
       return medikamentRepository.saveAll(importedMedikamente);
    }
}
