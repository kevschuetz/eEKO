package org.example.eko.service;

import org.example.eko.model.entities.DateEntity;
import org.example.eko.model.filerepresentations.DataSet;
import org.example.eko.model.entities.Medikament;
import org.example.eko.model.entities.WirkstoffAtcCode;
import org.example.eko.model.entities.Wirkstoff;
import org.example.eko.model.repositories.DateRepository;
import org.example.eko.model.repositories.MedikamentRepository;
import org.example.eko.model.repositories.WirkstoffRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImportService {
    private static Logger logger = LoggerFactory.getLogger(ImportService.class);

    private final MedikamentRepository medikamentRepository;
    private final WirkstoffRepository wirkstoffRepository;
    private final DateRepository dateRepository;

    public ImportService(MedikamentRepository medikamentRepository, WirkstoffRepository wirkstoffRepository, DateRepository dateRepository){
        this.medikamentRepository = medikamentRepository;
        this.wirkstoffRepository = wirkstoffRepository;
        this.dateRepository = dateRepository;
    }

    public void importDataSet(DataSet dataSet, LocalDate validDate){
        DateEntity dateEntity = dateRepository.save(new DateEntity(validDate));

        // atc codes
        List<WirkstoffAtcCode> wirkstoffAtcCodes = new ArrayList<>();
        for(var atcCodeFileEntry : dataSet.atcCodeFileEntries()){
            if(wirkstoffRepository.findById(atcCodeFileEntry.code()).isPresent()) continue;

            WirkstoffAtcCode wirkstoffAtcCode = new WirkstoffAtcCode();
            wirkstoffAtcCode.setAtcCode(atcCodeFileEntry.code());
            wirkstoffAtcCode.setText(atcCodeFileEntry.text());
            wirkstoffAtcCodes.add(wirkstoffAtcCode);
        }
        wirkstoffRepository.saveAll(wirkstoffAtcCodes);

        for(var wirkstoffAtcCode : wirkstoffAtcCodes){
            wirkstoffRepository.findById(wirkstoffAtcCode.getAtcCode().substring(0, 1)).ifPresent(wirkstoffAtcCode::setAnatomischeHauptgruppe);

            if(wirkstoffAtcCode.getAtcCode().length() >= 3 )
                wirkstoffRepository.findById(wirkstoffAtcCode.getAtcCode().substring(0, 3)).ifPresent(wirkstoffAtcCode::setTherapeutischeUntergruppe);

            if(wirkstoffAtcCode.getAtcCode().length() >= 4)
                wirkstoffRepository.findById(wirkstoffAtcCode.getAtcCode().substring(0, 4)).ifPresent(wirkstoffAtcCode::setPharmakologischeUntegruppe);

            if(wirkstoffAtcCode.getAtcCode().length() >= 5)
                wirkstoffRepository.findById(wirkstoffAtcCode.getAtcCode().substring(0, 5)).ifPresent(wirkstoffAtcCode::setChemischeUntergruppe);
        }
        wirkstoffRepository.saveAll(wirkstoffAtcCodes);

        // medikamente
        List<Medikament> medikaments = new ArrayList<>();
        for(var medikamentFileEntry : dataSet.medikamentFileEntries()){
            Medikament medikament = new Medikament();
            medikament.setValidDate(dateEntity);
            medikament.setPharmaNummer(medikamentFileEntry.pharmaNummer());
            medikament.setRegisterNummernPrefix(medikamentFileEntry.euRegisterNummerPrefix());
            medikament.setRegisterNummer(medikamentFileEntry.registerNummer());
            medikament.setEuRegisterNummernPackungsNummer(medikamentFileEntry.euRegisterNummerPackungsNummer());
            medikament.setPharmaNummer(medikamentFileEntry.pharmaNummer());
            medikament.setPreisModell(medikamentFileEntry.preisModell());
            medikament.setTeilbarkeit(medikamentFileEntry.teilbarkeit());
            medikament.setAbgabeAnzahl(medikamentFileEntry.abgabeAnzahl() != null ? medikamentFileEntry.abgabeAnzahl().intValue() : null);
            medikament.setKassenVerkaufsPreis(medikamentFileEntry.kvp());
            medikament.setKassenzeichen(medikamentFileEntry.kassenZeichen());
            medikament.setKvpEinheit(medikamentFileEntry.kvpEinheit());
            medikament.setDarreichungsForm(medikamentFileEntry.darreichungsform());
            medikament.setBox(medikamentFileEntry.box());
            medikament.setMenge(medikamentFileEntry.menge());
            medikament.setMengenart(medikamentFileEntry.mengenArt());
            medikament.setName(medikamentFileEntry.name());
            medikament.setPackungsHinweis(medikamentFileEntry.packungsHinweis());
            var hinweis = dataSet.hinweisFileEntries().stream().filter(h -> h.pharmaNummer().equals(medikament.getPharmaNummer())).findFirst();
            hinweis.ifPresent(hinweisFileEntry -> medikament.setHinweis(hinweisFileEntry.hinweis()));

            var regeltext = dataSet.regeltextFileEntries().stream().filter(r -> r.pharmaNummer().equals(medikament.getPharmaNummer())).findFirst();
            regeltext.ifPresent(regeltextFileEntry -> medikament.setRegeltext(regeltextFileEntry.regeltext()));

            List<Wirkstoff> wirkstoffs = new ArrayList<>();
            for(var wirkstoff : dataSet.wirkstoffFileEntries().stream().filter(w -> w.pharmaNummer().equals(medikament.getPharmaNummer())).collect(Collectors.toList())){
                var wirkstoffInformation = new Wirkstoff();
                wirkstoffRepository.findById(wirkstoff.pharAtcCode()).ifPresent(wirkstoffInformation::setPharWirkstoff);
                wirkstoffRepository.findById(wirkstoff.wirkAtcCode()).ifPresent(wirkstoffInformation::setWirkWirkstoff);
                wirkstoffInformation.setWirkstoffEigenschaft(wirkstoff.wirkstoffEigenschaft());
                wirkstoffInformation.setWirkstoffEigenschaft(wirkstoff.wirkstoffEigenschaft());
                wirkstoffInformation.setWirkstoffStärkenDimension(wirkstoff.wirkstoffStärkenDimension());
                wirkstoffInformation.setLaufNummer(wirkstoff.laufnummer());
                wirkstoffInformation.setWirkstoffStärke(wirkstoff.wirkstoffStärke());
                wirkstoffs.add(wirkstoffInformation);
            }
            medikament.setWirkstoffInformationen(wirkstoffs);

            medikaments.add(medikament);
        }
        medikamentRepository.saveAll(medikaments);
    }
}
