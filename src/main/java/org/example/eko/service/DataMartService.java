package org.example.eko.service;

import org.example.eko.model.entities.datamart.DateDimension;
import org.example.eko.model.entities.datamart.MedikamentDimension;
import org.example.eko.model.entities.datamart.VergleichsFakt;
import org.example.eko.model.entities.datamart.VergleichsKennzeichen;
import org.example.eko.model.entities.staging.Medikament;
import org.example.eko.model.repositories.staging.FaktRepository;
import org.example.eko.model.repositories.staging.MedikamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class DataMartService {
    private MedikamentRepository medikamentRepository;
    private FaktRepository faktRepository;

    public DataMartService(MedikamentRepository medikamentRepository, FaktRepository faktRepository){
        this.medikamentRepository = medikamentRepository;
        this.faktRepository = faktRepository;
    }

    @Transactional
    public void migrateDataForGivenDate(LocalDate date){
           medikamentRepository.findAll().stream().filter(med -> med.getValidFrom().getDate().equals(date)).forEach(med -> {
               med.getMedikamentVergleichsEntityList().stream().forEach(vergleich -> {
                   VergleichsFakt vergleichsFakt = new VergleichsFakt();
                   MedikamentDimension medikament = new MedikamentDimension();
                   DateDimension dateDimension = new DateDimension();
                   VergleichsKennzeichen vergleichsKennzeichen = new VergleichsKennzeichen();


                   vergleichsFakt.setDate(dateDimension);
                   vergleichsFakt.setMedikament(medikament);
                   vergleichsFakt.setVergleichsKennzeichen(vergleichsKennzeichen);

                   Medikament basis = med;
                   vergleichsFakt.setEinsparungsPotenzial(vergleich.getVergleichsMedikament().getKvpProEinheit() - basis.getKvpProEinheit());
                   vergleichsFakt.setKvpEinheit(basis.getKvpProEinheit());
                   vergleichsFakt.setKvp(basis.getKassenverkaufspreis());

                   medikament.setBox(basis.getBox().charAt(0));
                   medikament.setMedikamentName(basis.getName());
                   medikament.setPharmaNummer(basis.getPharmaNummer());
                   medikament.setDarreichungsForm(basis.getDarreichungsForm());
                   medikament.setKassenZeichen(basis.getKassenzeichen());
                   medikament.setPositionPreisvergleich(basis.getPositionPreisvergleich());
                   medikament.setSuchtgiftVignette(basis.getSuchtGiftVignette());
                   medikament.setLangzeitBewilligung(basis.getLangzeitBewilligung());
                   medikament.setRezeptPflicht(basis.getRezeptpflichtBezeichnung());
                   medikament.setRezeptPflichtId(basis.getRezeptPflichtId());
                   medikament.setPreisModell(basis.getPreisModell());

                   var wirkstoff = basis.getWirkstoffe().stream().findFirst().get().getPharWirkstoff();
                   medikament.setAnatomischeHauptgruppe(wirkstoff.getAnatomischeHauptgruppe().getAtcCode());
                   medikament.setAnatomischeHauptgruppeBezeichnung(wirkstoff.getAnatomischeHauptgruppe().getText());
                   medikament.setTherapeutischeUntergruppe(wirkstoff.getTherapeutischeUntergruppe().getAtcCode());
                   medikament.setTherapeutischeUntergruppeBezeichnung(wirkstoff.getTherapeutischeUntergruppe().getText());
                   medikament.setPharmakologischeUntergruppe(wirkstoff.getPharmakologischeUntegruppe().getAtcCode());
                   medikament.setPharmakolgischeUntergruppeBezeichnung(wirkstoff.getPharmakologischeUntegruppe().getText());
                   medikament.setChemischeUntergruppe(wirkstoff.getPharmakologischeUntegruppe().getAtcCode());
                   medikament.setChemischeUntergruppeBezeichnung(wirkstoff.getChemischeUntergruppe().getText());
                   medikament.setChemischeSubstanz(wirkstoff.getAtcCode().trim().length()==7?wirkstoff.getAtcCode():"-1");
                   medikament.setChemischeSubstanzBezeichnung(wirkstoff.getAtcCode().trim().length()==7?wirkstoff.getText():"-1");

                   medikament = new MedikamentDimension();
                   basis = vergleich.getVergleichsMedikament();
                   vergleichsFakt.setVergleichsMedikament(medikament);

                   vergleichsFakt.setEinsparungsPotenzial(vergleich.getVergleichsMedikament().getKvpProEinheit() - med.getKvpProEinheit());
                   vergleichsFakt.setKvpEinheit(basis.getKvpProEinheit());
                   vergleichsFakt.setKvp(basis.getKassenverkaufspreis());

                   medikament.setBox(basis.getBox().charAt(0));
                   medikament.setMedikamentName(basis.getName());
                   medikament.setPharmaNummer(basis.getPharmaNummer());
                   medikament.setDarreichungsForm(basis.getDarreichungsForm());
                   medikament.setKassenZeichen(basis.getKassenzeichen());
                   medikament.setPositionPreisvergleich(basis.getPositionPreisvergleich());
                   medikament.setSuchtgiftVignette(basis.getSuchtGiftVignette());
                   medikament.setLangzeitBewilligung(basis.getLangzeitBewilligung());
                   medikament.setRezeptPflicht(basis.getRezeptpflichtBezeichnung());
                   medikament.setRezeptPflichtId(basis.getRezeptPflichtId());
                   medikament.setPreisModell(basis.getPreisModell());

                   wirkstoff = basis.getWirkstoffe().stream().findFirst().get().getPharWirkstoff();
                   medikament.setAnatomischeHauptgruppe(wirkstoff.getAnatomischeHauptgruppe().getAtcCode());
                   medikament.setAnatomischeHauptgruppeBezeichnung(wirkstoff.getAnatomischeHauptgruppe().getText());
                   medikament.setTherapeutischeUntergruppe(wirkstoff.getTherapeutischeUntergruppe().getAtcCode());
                   medikament.setTherapeutischeUntergruppeBezeichnung(wirkstoff.getTherapeutischeUntergruppe().getText());
                   medikament.setPharmakologischeUntergruppe(wirkstoff.getPharmakologischeUntegruppe().getAtcCode());
                   medikament.setPharmakolgischeUntergruppeBezeichnung(wirkstoff.getPharmakologischeUntegruppe().getText());
                   medikament.setChemischeUntergruppe(wirkstoff.getPharmakologischeUntegruppe().getAtcCode());
                   medikament.setChemischeUntergruppeBezeichnung(wirkstoff.getChemischeUntergruppe().getText());
                   medikament.setChemischeSubstanz(wirkstoff.getAtcCode().trim().length()==7?wirkstoff.getAtcCode():"-1");
                   medikament.setChemischeSubstanzBezeichnung(wirkstoff.getAtcCode().trim().length()==7?wirkstoff.getText():"-1");

                   vergleichsKennzeichen.setKennzeichen(vergleich.getVergleichsKennzeichen());

                   dateDimension.setDate(med.getValidFrom().getDate());
                   dateDimension.setMonth(med.getValidFrom().getMonthOfYear());
                   dateDimension.setTheMonth(med.getValidFrom().getMonth());
                   dateDimension.setYear(med.getValidFrom().getYear());

                   faktRepository.save(vergleichsFakt);
               });
           });
    }
}
