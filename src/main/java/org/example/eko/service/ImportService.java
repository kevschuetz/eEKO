package org.example.eko.service;

import org.example.eko.model.entities.*;
import org.example.eko.model.filerepresentations.DataSet;
import org.example.eko.model.filerepresentations.HinweisFileEntry;
import org.example.eko.model.filerepresentations.RegeltextFileEntry;
import org.example.eko.model.filerepresentations.WirkstoffInformationFileEntry;
import org.example.eko.model.repositories.DateRepository;
import org.example.eko.model.repositories.MedikamentRepository;
import org.example.eko.model.repositories.WirkstoffRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class ImportService {
    private static Logger logger = LoggerFactory.getLogger(ImportService.class);

    private final MedikamentRepository medikamentRepository;
    private final WirkstoffRepository wirkstoffRepository;
    private final DateRepository dateRepository;

    public ImportService(MedikamentRepository medikamentRepository, WirkstoffRepository wirkstoffRepository, DateRepository dateRepository) throws Exception{
        this.medikamentRepository = medikamentRepository;
        this.wirkstoffRepository = wirkstoffRepository;
        this.dateRepository = dateRepository;
    }

    @Transactional
    public void importDataSet(DataSet dataSet, LocalDate validDate) throws Exception {
        logger.info("Trying to import dataset for {}.", validDate.toString());
        AtomicBoolean rejectDataset = new AtomicBoolean(false);
        dateRepository.findAll().forEach(d-> {
            logger.info("Found date {}", d.getDate().toString());
            logger.info("Is date equal? - {}", d.getDate().equals(validDate));
            if(d.getDate().equals(validDate)) rejectDataset.set(true);
        });
        if(rejectDataset.get()){
            logger.warn("Rejecting dataset for {}, as there is already date for this date.", validDate.toString());
            return;
        }else{
            logger.info("Importing dataset for {}.", validDate.toString());
        }

        DateEntity dateEntity = dateRepository.save(new DateEntity(validDate));
        // atc codes
        List<WirkstoffAtcCode> wirkstoffAtcCodes = new ArrayList<>();
        for(var atcCodeFileEntry : dataSet.atcCodeFileEntries()){
            WirkstoffAtcCode wirkstoffAtcCode = new WirkstoffAtcCode();
            wirkstoffAtcCode.setAtcCode(atcCodeFileEntry.code());
            wirkstoffAtcCode.setText(atcCodeFileEntry.text());
            wirkstoffAtcCode.setValidFrom(dateEntity);
            wirkstoffAtcCodes.add(wirkstoffAtcCode);
        }
        wirkstoffRepository.saveAll(wirkstoffAtcCodes);

        for(var wirkstoffAtcCode : wirkstoffAtcCodes){
            wirkstoffAtcCodes.stream().filter(ws -> ws.getAtcCode().trim().equals(wirkstoffAtcCode.getAtcCode().substring(0, 1))).findFirst().ifPresent(wirkstoffAtcCode::setAnatomischeHauptgruppe);

            if(wirkstoffAtcCode.getAtcCode().length() >= 3 )
                wirkstoffAtcCodes.stream().filter(ws -> ws.getAtcCode().trim().equals(wirkstoffAtcCode.getAtcCode().substring(0, 3))).findFirst().ifPresent(wirkstoffAtcCode::setTherapeutischeUntergruppe);

            if(wirkstoffAtcCode.getAtcCode().length() >= 4)
                wirkstoffAtcCodes.stream().filter(ws -> ws.getAtcCode().trim().equals(wirkstoffAtcCode.getAtcCode().substring(0, 4))).findFirst().ifPresent(wirkstoffAtcCode::setPharmakologischeUntegruppe);

            if(wirkstoffAtcCode.getAtcCode().length() >= 5)
                wirkstoffAtcCodes.stream().filter(ws -> ws.getAtcCode().trim().equals(wirkstoffAtcCode.getAtcCode().substring(0, 5))).findFirst().ifPresent(wirkstoffAtcCode::setChemischeUntergruppe);
        }
        wirkstoffRepository.saveAll(wirkstoffAtcCodes);

        // medikamente
        List<Medikament> medikaments = new ArrayList<>();
        for(var medikamentFileEntry : dataSet.medikamentFileEntries()){
            Medikament medikament = new Medikament();
            medikament.setValidFrom(dateEntity);
            medikament.setPharmaNummer(medikamentFileEntry.pharmaNummer());
            medikament.setRegisterNummernPrefix(medikamentFileEntry.euRegisterNummerPrefix());
            medikament.setRegisterNummer(medikamentFileEntry.registerNummer());
            medikament.setEuRegisterNummernPackungsNummer(medikamentFileEntry.euRegisterNummerPackungsNummer());
            medikament.setPharmaNummer(medikamentFileEntry.pharmaNummer());
            medikament.setTeilbarkeit(medikamentFileEntry.teilbarkeit());
            medikament.setDarreichungsForm(medikamentFileEntry.darreichungsform());
            medikament.setMenge(medikamentFileEntry.menge());
            medikament.setMengenart(medikamentFileEntry.mengenArt());
            medikament.setName(medikamentFileEntry.name());
            medikament.setPackungsHinweis(medikamentFileEntry.packungsHinweis());
            medikament.setBox(medikamentFileEntry.box());
            medikament.setAbgabeanzahl(medikamentFileEntry.abgabeAnzahl());
            medikament.setKassenzeichen(medikamentFileEntry.kassenZeichen());
            medikament.setPreisModell(medikamentFileEntry.preisModell());
            medikament.setKassenverkaufspreis(medikamentFileEntry.kvp());
            medikament.setKvpProEinheit(medikamentFileEntry.kvpEinheit());

            dataSet.wirkstoffInformationFileEntries()
                    .stream()
                    .filter(wi -> wi.pharmaNummer().equals(medikament.getPharmaNummer()))
                    .map(WirkstoffInformationFileEntry::wirkstoffInformation)
                    .findFirst()
                    .ifPresent(medikament::setWirkstoffInformation);

            List<Wirkstoff> wirkstoffs = new ArrayList<>();
            for(var wirkstoff : dataSet.wirkstoffFileEntries().stream().filter(w -> w.pharmaNummer().equals(medikament.getPharmaNummer())).collect(Collectors.toList())){
                var wirkstoffInformation = new Wirkstoff();
                wirkstoffAtcCodes.stream().filter(atc -> wirkstoff.pharAtcCode().equals(atc.getAtcCode())).findFirst().ifPresent(wirkstoffInformation::setPharWirkstoff);
                wirkstoffAtcCodes.stream().filter(atc -> atc.getAtcCode().equals(wirkstoff.wirkAtcCode())).findFirst().ifPresent(wirkstoffInformation::setWirkWirkstoff);
                wirkstoffInformation.setWirkstoffEigenschaft(wirkstoff.wirkstoffEigenschaft());
                wirkstoffInformation.setWirkstoffStärkenDimension(wirkstoff.wirkstoffStärkenDimension());
                wirkstoffInformation.setLaufNummer(wirkstoff.laufnummer());
                wirkstoffInformation.setWirkstoffStärke(wirkstoff.wirkstoffStärke());
                wirkstoffs.add(wirkstoffInformation);
            }
            medikament.setWirkstoffe(wirkstoffs);

            dataSet.hinweisFileEntries()
                    .stream()
                    .filter(h -> h.pharmaNummer().equals(medikament.getPharmaNummer()))
                    .map(HinweisFileEntry::hinweis)
                    .findFirst()
                    .ifPresent(medikament::setHinweis);

            dataSet.regeltextFileEntries()
                    .stream()
                    .filter(r -> r.pharmaNummer().equals(medikament.getPharmaNummer()))
                    .map(RegeltextFileEntry::regeltext)
                    .findFirst().ifPresent(medikament::setRegelText);

            dataSet.medikamentZusatzFileEntries()
                    .stream()
                    .filter(mz -> mz.pharmaNummer().equals(medikament.getPharmaNummer()))
                    .findFirst()
                    .ifPresent(mz -> {
                        medikament.setLangzeitBewilligung(mz.langzeitBewilligung());
                        medikament.setSuchtGiftVignette(mz.suchtGiftVignette());
                        });

            dataSet.rezeptpflichtFileEntries()
                    .stream()
                    .filter(rz -> rz.pharmaNummer().equals(medikament.getPharmaNummer())).findFirst().ifPresent(rz -> {
                        medikament.setRezeptPflichtId(rz.isRezeptpflichtig());
                        medikament.setRezeptpflichtBezeichnung(rz.rezeptpflichtHinweis());
                    });

            dataSet.indTextFileEntries()
                    .stream()
                    .filter(indText -> indText.pharmaNummer().equals(medikament.getPharmaNummer())).findFirst().ifPresent(indText -> {
                        medikament.setIndText(indText.indikationsText());
                    });
            medikaments.add(medikament);
        }
        medikamentRepository.saveAll(medikaments);

        for(var vergleichsEntry : dataSet.vergleichsdateiFileEntries()){
            medikaments.stream().filter(m -> m.getPharmaNummer().equals(vergleichsEntry.pharmanummer())).findFirst().ifPresent(m -> {
                        m.setPositionPreisvergleich(vergleichsEntry.positionPreisvergleich());
                        List<MedikamentVergleichsEntity> vergleiche = new ArrayList<>();
                        for(var x : vergleichsEntry.substitutionsMedikamente()){
                            MedikamentVergleichsEntity vergleich = new MedikamentVergleichsEntity();
                            vergleich.setPositionVergleichsMedikament(x.positionPreisvergleich());
                            vergleich.setVergleichsKennzeichen(x.vergleichsKennzeichen());
                            medikaments.stream().filter(vm -> vm.getPharmaNummer().equals(x.pharmaNummer())).findFirst().ifPresent(vergleich::setVergleichsMedikament);

                            vergleiche.add(vergleich);
                        }
                        m.setMedikamentVergleichsEntityList(vergleiche);
                        medikamentRepository.save(m);
                    });
        }
    }
}
