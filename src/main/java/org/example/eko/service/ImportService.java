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
import java.util.Optional;
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

    @Transactional
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
            Optional<Medikament> optionalMedikament = medikamentRepository.findById(medikamentFileEntry.pharmaNummer());
            Medikament medikament;
            if(optionalMedikament.isEmpty()){
                medikament = new Medikament();
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
                medikament.setEkoEintraege(new ArrayList<>());

                dataSet.wirkstoffInformationFileEntries()
                        .stream()
                        .filter(wi -> wi.pharmaNummer().equals(medikament.getPharmaNummer()))
                        .map(WirkstoffInformationFileEntry::wirkstoffInformation)
                        .findFirst()
                        .ifPresent(medikament::setWirkstoffInformation);

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
                medikament.setWirkstoffe(wirkstoffs);
            }else{
                medikament = optionalMedikament.get();
            }

            EkoEintrag ekoEintrag = new EkoEintrag();
            ekoEintrag.setValidFrom(dateEntity);
            ekoEintrag.setBox(medikamentFileEntry.box());
            ekoEintrag.setAbgabeanzahl(medikamentFileEntry.abgabeAnzahl());
            ekoEintrag.setKassenzeichen(medikamentFileEntry.kassenZeichen());
            ekoEintrag.setPreisModell(medikamentFileEntry.preisModell());
            ekoEintrag.setKassenverkaufspreis(medikamentFileEntry.kvp());
            ekoEintrag.setKvpProEinheit(medikamentFileEntry.kvpEinheit());

            dataSet.hinweisFileEntries()
                    .stream()
                    .filter(h -> h.pharmaNummer().equals(medikament.getPharmaNummer()))
                    .map(HinweisFileEntry::hinweis)
                    .findFirst()
                    .ifPresent(ekoEintrag::setHinweis);

            dataSet.regeltextFileEntries()
                    .stream()
                    .filter(r -> r.pharmaNummer().equals(medikament.getPharmaNummer()))
                    .map(RegeltextFileEntry::regeltext)
                    .findFirst().ifPresent(ekoEintrag::setRegelText);

            dataSet.medikamentZusatzFileEntries()
                    .stream()
                    .filter(mz -> mz.pharmaNummer().equals(medikament.getPharmaNummer()))
                    .findFirst()
                    .ifPresent(mz -> {
                        ekoEintrag.setLangzeitBewilligung(mz.langzeitBewilligung());
                        ekoEintrag.setSuchtGiftVignette(mz.suchtGiftVignette());
                        });

            dataSet.rezeptpflichtFileEntries()
                    .stream()
                    .filter(rz -> rz.pharmaNummer().equals(medikament.getPharmaNummer())).findFirst().ifPresent(rz -> {
                        ekoEintrag.setRezeptPflichtId(rz.isRezeptpflichtig());
                        ekoEintrag.setRezeptpflichtBezeichnung(rz.rezeptpflichtHinweis());
                    });

            dataSet.indTextFileEntries()
                    .stream()
                    .filter(indText -> indText.pharmaNummer().equals(medikament.getPharmaNummer())).findFirst().ifPresent(indText -> {
                        ekoEintrag.setIndText(indText.indikationsText());
                    });

            if(medikament.getEkoEintraege() == null) medikament.setEkoEintraege(new ArrayList<>());
            medikament.getEkoEintraege().add(ekoEintrag);

            medikaments.add(medikament);
        }
        medikamentRepository.saveAll(medikaments);

        for(var vergleichsEntry : dataSet.vergleichsdateiFileEntries()){
            medikamentRepository.findById(vergleichsEntry.pharmanummer()).ifPresent(m -> m.getEkoEintraege()
                    .stream()
                    .filter(e -> e.getValidFrom().getDate().equals(validDate))
                    .findFirst()
                    .ifPresent(entry -> {
                        entry.setPositionPreisvergleich(vergleichsEntry.positionPreisvergleich());
                        List<MedikamentVergleichsEntity> vergleiche = new ArrayList<>();
                        for(var x : vergleichsEntry.substitutionsMedikamente()){
                            MedikamentVergleichsEntity vergleich = new MedikamentVergleichsEntity();
                            vergleich.setPositionVergleichsMedikament(x.positionPreisvergleich());
                            vergleich.setVergleichsKennzeichen(x.vergleichsKennzeichen());
                            medikamentRepository.findById(x.pharmaNummer()).ifPresent(vergleich::setVergleichsMedikament);

                            vergleiche.add(vergleich);
                        }

                        entry.setMedikamentVergleichsEntityList(vergleiche);
                        medikamentRepository.save(m);
                    }));
        }
    }
}
