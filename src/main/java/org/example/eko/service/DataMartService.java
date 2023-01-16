package org.example.eko.service;

import org.example.eko.model.entities.datamart.DatumDimension;
import org.example.eko.model.entities.datamart.HeilmittelDimension;
import org.example.eko.model.entities.datamart.VergleichsFakt;
import org.example.eko.model.entities.datamart.VergleichsKennzeichenDimension;
import org.example.eko.model.entities.staging.Medikament;
import org.example.eko.model.repositories.staging.FaktRepository;
import org.example.eko.model.repositories.staging.MedikamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class DataMartService {
    private static final Map<Integer, String> vgkBezeichnung = new HashMap<>();
    private MedikamentRepository medikamentRepository;
    private FaktRepository faktRepository;

    static{
        vgkBezeichnung.put(1, "Gleicher Wirkstoff, Gleiche Stärke.");
        vgkBezeichnung.put(2, "Gleicher ATC Code, vergleichbare Stärke, Geringfügige Unterschiede.");
        vgkBezeichnung.put(3, "Ähnlicher Wirkstoff, vergelichbare Stärke.");
    }

    public DataMartService(MedikamentRepository medikamentRepository, FaktRepository faktRepository){
        this.medikamentRepository = medikamentRepository;
        this.faktRepository = faktRepository;
    }

    @Transactional
    public void migrateDataForGivenDate(LocalDate date){
           medikamentRepository.findAll().stream().filter(med -> med.getValidFrom().getDate().equals(date)).forEach(med -> {
               med.getMedikamentVergleichsEntityList().stream().forEach(vergleich -> {

                   if(vergleich.getVergleichsMedikament().getKvpProEinheit() - med.getKvpProEinheit() <= 0) {

                       VergleichsFakt vergleichsFakt = new VergleichsFakt();
                       HeilmittelDimension medikament = new HeilmittelDimension();
                       DatumDimension datumDimension = new DatumDimension();
                       VergleichsKennzeichenDimension vergleichsKennzeichenDimension = new VergleichsKennzeichenDimension();


                       vergleichsFakt.setD_date(datumDimension);
                       vergleichsFakt.setD_heilmittel(medikament);
                       vergleichsFakt.setVergleichsKennzeichen(vergleichsKennzeichenDimension);

                       Medikament basis = med;
                       vergleichsFakt.setF_einsparungsPotenzial((vergleich.getVergleichsMedikament().getKvpProEinheit() - basis.getKvpProEinheit())*-1);
                       medikament.setKvpEinheit(basis.getKvpProEinheit());
                       vergleichsFakt.setF_kvp(basis.getKassenverkaufspreis());
                       medikament.setBox(basis.getBox().charAt(0));
                       medikament.setMedikamentName(basis.getName());
                       medikament.setPharmaNummer(basis.getPharmaNummer());
                       medikament.setDarreichungsForm(basis.getDarreichungsForm()!=null?basis.getDarreichungsForm():"Keine Darreichungsform");
                       medikament.setKassenZeichen(basis.getKassenzeichen()!=null?basis.getKassenzeichen():"Kein Kassenzeichen");
                       medikament.setPositionPreisvergleich(basis.getPositionPreisvergleich());
                       medikament.setSuchtgiftVignette(basis.getSuchtGiftVignette()!=null?basis.getSuchtGiftVignette():"Keine Suchtgiftvignette");
                       medikament.setLangzeitBewilligung(basis.getLangzeitBewilligung()!=null?basis.getLangzeitBewilligung():"Keine Langzeitbewilligung");
                       medikament.setRezeptPflicht(basis.getRezeptpflichtBezeichnung());
                       medikament.setRezeptPflichtId(basis.getRezeptPflichtId());
                       medikament.setPreisModell(basis.getPreisModell()!=null?basis.getPreisModell():"Kein Preismodell");
                       medikament.setMenge(basis.getMenge());
                       medikament.setMengenArt(basis.getMengenart());

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

                       medikament = new HeilmittelDimension();
                       basis = vergleich.getVergleichsMedikament();
                       vergleichsFakt.setD_vergleichsHeilmittel(medikament);

                       vergleichsFakt.setF_einsparungsPotenzial((vergleich.getVergleichsMedikament().getKvpProEinheit() - med.getKvpProEinheit())*-1);
                       medikament.setKvpEinheit(basis.getKvpProEinheit());
                       vergleichsFakt.setF_kvp(basis.getKassenverkaufspreis());

                       medikament.setBox(basis.getBox().charAt(0));
                       medikament.setMedikamentName(basis.getName());
                       medikament.setPharmaNummer(basis.getPharmaNummer());
                       medikament.setDarreichungsForm(basis.getDarreichungsForm()!=null?basis.getDarreichungsForm():"Keine Darreichungsform");
                       medikament.setKassenZeichen(basis.getKassenzeichen()!=null?basis.getKassenzeichen():"Kein Kassenzeichen");
                       medikament.setPositionPreisvergleich(basis.getPositionPreisvergleich());
                       medikament.setSuchtgiftVignette(basis.getSuchtGiftVignette()!=null?basis.getSuchtGiftVignette():"Keine Suchtgiftvignette");
                       medikament.setLangzeitBewilligung(basis.getLangzeitBewilligung()!=null?basis.getLangzeitBewilligung():"Keine Langzeitbewilligung");
                       medikament.setRezeptPflicht(basis.getRezeptpflichtBezeichnung());
                       medikament.setRezeptPflichtId(basis.getRezeptPflichtId());
                       medikament.setPreisModell(basis.getPreisModell()!=null?basis.getPreisModell():"Kein Preismodell");
                       medikament.setMenge(basis.getMenge());
                       medikament.setMengenArt(basis.getMengenart());


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

                       vergleichsKennzeichenDimension.setKennzeichen(vergleich.getVergleichsKennzeichen());
                       vergleichsKennzeichenDimension.setBezeichnung(vgkBezeichnung.get(vergleich.getVergleichsKennzeichen()));

                       datumDimension.setDate(med.getValidFrom().getDate());
                       datumDimension.setMonth(med.getValidFrom().getMonthOfYear());
                       datumDimension.setTheMonth(med.getValidFrom().getMonth());
                       datumDimension.setYear(med.getValidFrom().getYear());

                       faktRepository.save(vergleichsFakt);
                   }
               });
           });
    }
}
